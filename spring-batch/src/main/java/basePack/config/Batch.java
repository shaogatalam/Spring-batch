package basePack.config;
import basePack.entity.Customer;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class Batch {

    //@Autowired
    //public JobRepository jobRepository;

    //@Autowired
    //public PlatformTransactionManager platformTransactionManager;

    @Autowired
    public DataSource dataSource;

    //    public Batch(DataSource dataSource) {
    //        this.dataSource = dataSource;
    //    }
    @Autowired
    public Listeners listeners;

    @Bean
    public Job sendMail(TaskExecutor taskExecutor, JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("JobA",jobRepository)
            .start(mailerStep(taskExecutor,jobRepository,transactionManager))
            .build();
    }

    @Bean
    public Step mailerStep(TaskExecutor taskExecutor, JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("stepA", jobRepository)
            .<Customer, PersonalizedEmail>chunk(15, transactionManager)
            .reader(itemReader(dataSource, queryProvider()))
            .processor(new CustomerProcessor())
            .writer(emailSendWriter())
            .taskExecutor(taskExecutor())
            .listener((ItemReadListener<? super Customer>) listeners)
            .listener((ItemProcessListener<? super Customer, ? super PersonalizedEmail>) listeners)
            .build();
    }

    @Bean
    public JdbcPagingItemReader<Customer> itemReader(DataSource dataSource, SqlPagingQueryProviderFactoryBean queryProvider) throws Exception {

        //Map<String, Object> parameterValues = new HashMap<>();
        //parameterValues.put("status", "NEW");

        return new JdbcPagingItemReaderBuilder<Customer>()
                .name("customerReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider.getObject())
                //.parameterValues(parameterValues)
                .rowMapper(customerMapper())
                .pageSize(20)
                .build();
    }

    private RowMapper<Customer> customerMapper() {
        return new CustomerRowMapper();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider() {
        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
        provider.setDataSource(dataSource); // Make sure the DataSource is properly injected
        provider.setSelectClause("select id, name, email, address, revenue");
        provider.setFromClause("from customer");
        //provider.setWhereClause("where status = :status");
        provider.setSortKey("id");
        return provider;
    }


    @Bean
    public ItemProcessor<Customer, PersonalizedEmail> CustomerProcessor() {return new CustomerProcessor();}

    @Bean
    public ItemWriter<PersonalizedEmail> emailSendWriter() {
        return new EmailWriter();
    }


//    @Bean
//    public TaskExecutor taskExecutor() {
//        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        simpleAsyncTaskExecutor.setConcurrencyLimit(10);
//        return simpleAsyncTaskExecutor;
//    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Minimum number of threads that are always kept alive
        executor.setMaxPoolSize(50);  // Maximum number of threads that can be created
        executor.setQueueCapacity(100); // Queue capacity for tasks that can't be executed immediately
        executor.setThreadNamePrefix("AsyncTaskExecutor-"); // Custom thread name prefix for easier debugging
        executor.setWaitForTasksToCompleteOnShutdown(true); // Wait for tasks to complete on shutdown
        executor.setAwaitTerminationSeconds(60); // Maximum wait time for task completion on shutdown
        executor.initialize(); // Initialize the executor
        return executor;
    }
}

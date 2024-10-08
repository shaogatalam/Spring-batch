package basePack.config;

import basePack.entity.Customer;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class Listeners implements ItemReadListener<Customer>, ItemProcessListener<Customer, PersonalizedEmail>, ItemWriteListener<PersonalizedEmail> {

    // Item-Read-Listener
    @Override
    public void beforeRead() {
        //System.out.println("Before reading item...");
    }

    @Override
    public void afterRead(Customer item) {
        //System.out.println("After reading item: " + item);
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("Error occurred while reading item: " + ex.getMessage());
    }

    // Item-Process-Listener
    @Override
    public void beforeProcess(Customer item) {
        //System.out.println("Before processing item: " + item);
    }

    @Override
    public void afterProcess(Customer item, PersonalizedEmail result) {
        //System.out.println("After processing item: " + item + " to result: " + result);
    }

    @Override
    public void onProcessError(Customer item, Exception e) {
        System.out.println("Error occurred while processing item: " + item);
    }

    // Item-Write-Listener
    @Override
    public void beforeWrite(Chunk<? extends PersonalizedEmail> items) {
        //System.out.println("Before writing items: " + items);
    }

    @Override
    public void afterWrite(Chunk<? extends PersonalizedEmail> items) {
        //System.out.println("After writing items: " + items);
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends PersonalizedEmail> items) {
        System.out.println("Error occurred while writing items: " + exception.getMessage());
    }
}

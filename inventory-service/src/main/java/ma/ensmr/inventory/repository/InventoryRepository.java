package ma.ensmr.inventory.repository;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class InventoryRepository {
    private final Map<String, Integer> stock = new ConcurrentHashMap<>(Map.of(
            "BOOK-001", 12,
            "BOOK-002", 3,
            "BOOK-003", 0
    ));
    public int findStock(String sku) {
        return stock.getOrDefault(sku, -1);
    }
    public boolean reserve(String sku, int quantity) {
        int available = findStock(sku);
        if (available < 0 || available < quantity) {
            return false;
        }
        stock.put(sku, available - quantity);
        return true;
    }
}
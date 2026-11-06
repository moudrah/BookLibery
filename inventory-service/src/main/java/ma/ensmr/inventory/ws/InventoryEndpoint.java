package ma.ensmr.inventory.ws;
import ma.ensmr.inventory.model.GetStockRequest;
import ma.ensmr.inventory.model.GetStockResponse;
import ma.ensmr.inventory.model.ReserveRequest;
import ma.ensmr.inventory.model.ReserveResponse;
import ma.ensmr.inventory.repository.InventoryRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
@Endpoint
public class InventoryEndpoint {
    public static final String NAMESPACE = "http://bookorder.example/inventory";
    private final InventoryRepository repository;
    public InventoryEndpoint(InventoryRepository repository) {
        this.repository = repository;
    }
    @PayloadRoot(namespace = NAMESPACE, localPart = "getStockRequest")
    @ResponsePayload
    public GetStockResponse getStock(@RequestPayload GetStockRequest request) {
        int available = repository.findStock(request.getSku());
        GetStockResponse response = new GetStockResponse();
        response.setAvailable(available);
        response.setWarehouse("PAR-01");
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE, localPart = "reserveRequest")
    @ResponsePayload
    public ReserveResponse reserve(@RequestPayload ReserveRequest request) {
        boolean ok = repository.reserve(request.getSku(), request.getQuantity());
        ReserveResponse response = new ReserveResponse();
        response.setReserved(ok);
        response.setMessage(ok
                ? "Stock reserve pour " + request.getQuantity() + " exemplaire(s)"
                : "Stock insuffisant ou SKU inconnu");
        return response;
    }
}
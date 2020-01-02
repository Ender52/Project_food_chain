import exceptions.WrongProductTypeException;

public interface Storage {
    void put(Product[] products) throws WrongProductTypeException;

    Product get(ProductType type);

    Product[] getProducts(ProductType type, int amount);

    boolean has(ProductType type, int amount) throws WrongProductTypeException;

}

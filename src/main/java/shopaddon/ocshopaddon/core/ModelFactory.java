package shopaddon.ocshopaddon.core;

import shopaddon.ocshopaddon.model.database.DataModel;
import shopaddon.ocshopaddon.model.database.DatabaseCommunicator;
import shopaddon.ocshopaddon.model.shop.ShopManager;
import shopaddon.ocshopaddon.model.shop.ShopModel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ModelFactory {

    private static ModelFactory instance;
    private final DataModel dataModel = new DatabaseCommunicator();
    private final ShopModel shopModel = new ShopManager(dataModel);
    private static final Lock lock = new ReentrantLock();

    public static ModelFactory getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ModelFactory();
                }
            }
        }
        return instance;
    }

    private ModelFactory() {
    }

    //LATER INITIATE THE GETTERS BELOW TO ONLY CREATE THE MODELS WHEN THE GETTERS ARE CALLED.

    public DataModel getDataModel() {
        return dataModel;
    }

    public ShopModel getShopModel() {
        return shopModel;
    }



}

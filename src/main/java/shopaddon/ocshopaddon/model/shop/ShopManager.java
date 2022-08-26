package shopaddon.ocshopaddon.model.shop;

import shopaddon.ocshopaddon.model.database.DataModel;

import java.util.ArrayList;

public class ShopManager implements ShopModel {

    DataModel dataModel;

    public ShopManager(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public Shop getShop(String shopUID) {
        return dataModel.getShop(shopUID);
    }

    @Override
    public ArrayList<Shop> getShopList() {
        return dataModel.getShopList();
    }

    @Override
    public void updateShop(Shop shop) {
        dataModel.updateShop(shop);
    }
}

package shopaddon.ocshopaddon.model.database;

import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.core.ConnectionHandler;
import shopaddon.ocshopaddon.core.RegionHandler;
import shopaddon.ocshopaddon.model.shop.Shop;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCommunicator implements DataModel {

    private static final Connection connection = ConnectionHandler.getInstance().getConnection();

    public DatabaseCommunicator() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createShop(Shop shop) {
        try {

            final String CREATE_SHOP = """
            insert into shopAddon.shop(shop_uid, shop_nick, owner)
            values (?,?,?);
            """;

            PreparedStatement stmt = connection.prepareStatement(CREATE_SHOP);

            stmt.setString(1, shop.getShopUID());
            stmt.setString(2, shop.getShopNick());
            stmt.setString(3, "server");

            stmt.executeUpdate();

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Shop getShop(String shopUID) {

        Shop shop = null;

        try {

            Statement stmt = connection.createStatement();

            final String GET_SHOP = "select * from shopaddon.shop where shop_uid = '" + shopUID + "';";

            ResultSet resultSet = stmt.executeQuery(GET_SHOP);

            while (resultSet.next()) {
                shop = new Shop(
                        RegionHandler.getRegion(resultSet.getString("shop_uid")),
                        resultSet.getString("owner"),
                        resultSet.getString("shop_nick")
                );
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    private static void resetInactive(Player player) {
        try {

            final String RESET_DAYS =
                    "update shopaddon.player set inactive_days = 0 where uuid = '" + player.getUniqueId() + "';";

            PreparedStatement prestmt = connection.prepareStatement(RESET_DAYS);

            prestmt.executeUpdate();

            prestmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addInactive() {
        try {

            final String INCREMENT_DAYS = "update shopaddon.player set inactive_days = inactive_days + 1;";

            PreparedStatement prestmt = connection.prepareStatement(INCREMENT_DAYS);

            prestmt.executeUpdate();

            prestmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkPlayer(Player player) {
        ArrayList<String> uuidList = new ArrayList<>();

        try {
            ResultSet resultSet = connection.prepareStatement("""
                select * from shopaddon.player;
                """).executeQuery();

            while (resultSet.next()) {
                uuidList.add(resultSet.getString("uuid"));
            }

            if (!(uuidList.contains(player.getUniqueId().toString()))) {
                addPlayer(player);
            }
            else
                resetInactive(player);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void addPlayer(Player player) {

        try {
            PreparedStatement stmt = connection.prepareStatement("""
                    insert into shopaddon.player(uuid)
                    values (?);
                    """);

            stmt.setString(1, player.getUniqueId().toString());

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public ArrayList<Shop> getShopList() {

        ArrayList<Shop> shopList = new ArrayList<>();

        try {

            Statement stmt = connection.createStatement();

            final String GET_SHOP = "select * from shopaddon.shop;";

            ResultSet resultSet = stmt.executeQuery(GET_SHOP);

            while (resultSet.next()) {
                shopList.add(new Shop(
                        RegionHandler.getRegion(resultSet.getString("shop_uid")),
                        resultSet.getString("owner"),
                        resultSet.getString("shop_nick")
                ));
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopList;
    }

    @Override
    public void removeShop(String shopUID) {

        String DELETE = """
                delete from shopaddon.shop
                where shop_uid = '%s'
                """.formatted(shopUID);

        try {

            PreparedStatement stmt = connection.prepareStatement(DELETE);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateShop(Shop shop) {

        String UPDATE = """
            update shopaddon.shop
            set shop_nick = '%s',
                owner = '%s'
            where
                shop_uid = '%s';
            """.formatted(shop.getShopNick(), shop.getOwnerUUID(), shop.getShopUID());

        try {

            PreparedStatement stmt = connection.prepareStatement(UPDATE);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetShops() {

        ArrayList<String> expiredUUID = new ArrayList<>();

        try {
            ResultSet resultSet = connection.prepareStatement("""
                select * from shopaddon.player
                where inactive_days > 30
                """).executeQuery();

            while (resultSet.next()) {
                expiredUUID.add(resultSet.getString("uuid"));
            }

            for (String index : expiredUUID) {
                PreparedStatement prestmt = connection.prepareStatement("""
                    update shopaddon.shop set owner = 'server'
                    where owner = %s
                        """.formatted(index)
                );

                prestmt.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

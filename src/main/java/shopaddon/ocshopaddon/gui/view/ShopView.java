/*package shopaddon.ocshopaddon.gui.view;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShopView {

    private Inventory guiTemplate;
    private Inventory gui;
    private ArrayList<ItemStack> shopList;
    private final int maxSize = 54;
    private final ItemStack tempItem = new ItemStack(Material.SPONGE, 1);
    private final ItemStack next = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
    private final ItemStack prev = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
    private final ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);

    public ShopView() {
        gui = Bukkit.createInventory(null,maxSize);
        shopList = GUIItems.INSTANCE.getShopsAsItems();
        initTemplate();
    }

    public Inventory getGUI(Player player) {
        return initView(player);
    }

    private Inventory initView(Player player) {
        gui.clear();

        for (int i = 0; i < 30; i++) {
            gui.addItem(shopList.)
        }

        guiTemplate.remove(tempItem);

        return gui;
    }

    private void initTemplate() {
        guiTemplate = Bukkit.createInventory(null, 54);

        guiTemplate.setItem(3, border);
        guiTemplate.setItem(12, border);
        guiTemplate.setItem(21, border);
        guiTemplate.setItem(30, border);
        guiTemplate.setItem(39, border);
        guiTemplate.setItem(48, border);

        guiTemplate.setItem(47,next);
        guiTemplate.setItem(47,prev);

        for (int i = 0; i < 48 ;i++) {
            if (i == 3 || i == 12 || i == 21 || i == 30 || i == 39)
                i = i + 6;

            guiTemplate.setItem(i, tempItem);
        }

    }

}
*/

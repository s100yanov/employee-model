package EmployeeModel;

import java.util.ArrayList;
import java.util.List;

class AssetsAllocator {

    List<String> defaultAssetsAllocator() {
        List<String> defaultAssets = new ArrayList<>();
        defaultAssets.add("Office Access Card");
        defaultAssets.add("Laptop Computer");
        defaultAssets.add("Mobile Phone");

        return defaultAssets;
    }

    void bonusAssetsAllocator(int level, List<String> assets) {
        if (level == 2) {
            assets.add("Car");
        }
        else  if (level == 3 || level == 4){
            assets.add("Car");
            assets.add("Office");
        }
    }
}

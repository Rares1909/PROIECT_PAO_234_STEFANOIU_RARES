package Service.Impl;

import Service.FoodService;
import Service.MainService;
import model.Food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {

    private static FoodServiceImpl instance=null;

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FoodServiceImpl(){}

    public static FoodServiceImpl getInstance(){
        if (instance==null){
            instance= new FoodServiceImpl();
        }
        return instance;
    }
    private static List<Food>items=new ArrayList<Food>();
    @Override
    public void addItem(Food food) throws IOException {
        items.add(food);
        service.Audit("Add food");
    }

    @Override
    public List<Food> getItems() throws IOException {
        service.Audit("Get food");
        return items;
    }
}

package Interfaces;

import java.util.ArrayList;

import Database.ProductDAO;
import Model.Product;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Grid;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class MainInterface extends Container{
	private ProductDAO productDAO;
	private Grid grid;
	private ArrayList<Product> products; 
	// Initialize the user interface
	public void initUI() {
		Button add = new Button("Add");
		add.appId = TCMain.addButId;
		
		Button update = new Button("Details");
		update.appId = TCMain.detailsButId;
		
		
		add(add,LEFT+1,TOP+1,PREFERRED,PREFERRED);
		add(update,RIGHT-1,TOP+1,PREFERRED,PREFERRED);

		productDAO = new ProductDAO();
		
		grid = new Grid(new String[] { "Id","Name", "Description"}, 
				new int[] { -15, -35,-50}, 
				new int [] {CENTER, CENTER,CENTER},
				false);
		add(grid, LEFT, AFTER + 2, FILL, FILL);
		
		grid.setItems(getGrid());
		
	}
	
	public void onEvent(Event e){
		switch (e.type) {
			case ControlEvent.PRESSED:
				switch (((Control) e.target).appId) {
	
					case TCMain.addButId:
						new EditInterface(productDAO,products.get(products.size()-1).getId()).popupNonBlocking();
						
						break;
					case TCMain.detailsButId:
						String[] productGrid = grid.getSelectedItem();
						
						Product product = new Product();
						product.setId(Integer.valueOf(productGrid[0]));
						product.setName(productGrid[1]);
						product.setDescription(productGrid[2]);
						
						new EditInterface(product,productDAO).popupNonBlocking();
						
						break;
				}
				break;
				
			case ControlEvent.WINDOW_CLOSED:
				if(e.target instanceof EditInterface){
					grid.setItems(getGrid());
				}
				break;
		}
		
	}
	
	public String[][] getGrid(){
		products = productDAO.getAll();
		
		String[][] gridValues = new String[products.size()][3];
		for(int i=0;i<products.size();i++){
			gridValues[i][2] = products.get(i).getDescription();
			gridValues[i][1] = products.get(i).getName();
			gridValues[i][0] = String.valueOf(products.get(i).getId());
		}
		return gridValues;
	}
}

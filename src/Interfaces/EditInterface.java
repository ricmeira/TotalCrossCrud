package Interfaces;

import Database.ProductDAO;
import Model.Product;
import totalcross.ui.Button;
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class EditInterface extends Window{
	private Edit name, description;
	private String type;
	public static final String insert= "insert";
	public static final String update = "update";
	private ProductDAO productDAO;
	private int lastId;
	private Product product;
	
	public EditInterface(ProductDAO dao,int lastId){
		type = insert;
		productDAO = dao;
		this.lastId = lastId;
	}
	
	public EditInterface(Product product,ProductDAO dao){
		type = update;
		productDAO = dao;
		this.product = product;
	}
	
	protected void postPopup(){
		super.postPopup();
		initUI();
	}
	
	public void initUI() {
		
		name = new Edit();
		description = new Edit();
		
		add(new Label("Name"), LEFT + 2, AFTER);
		add(name, SAME, AFTER  );
		add(new Label("Description"), LEFT + 2, AFTER);
		add(description, SAME, AFTER  );
		
		if(type.equals(insert)){
			Button addBut = new Button("Add");
			addBut.appId = TCMain.addButId;
			add(addBut,LEFT,AFTER+5,PREFERRED, PREFERRED);
		}
		else{
			if(type.equals(update)){
				Button deleteBut = new Button("Delete");
				deleteBut.appId = TCMain.deleteButId;
				add(deleteBut,LEFT+1,AFTER+10,PREFERRED, PREFERRED);
				
				Button updateBut = new Button("Update");
				updateBut.appId = TCMain.updateButId;
				add(updateBut,RIGHT-1,SAME,PREFERRED, PREFERRED);
				
				name.setText(product.getName());
				description.setText(product.getDescription());
			}
		}
	}
	
	public void onEvent(Event e){
		switch (e.type) {

			case ControlEvent.PRESSED:
				Product prod = new Product();
				prod.setDescription(description.getText());
				prod.setName(name.getText());
				
				switch (((Control) e.target).appId) {
	
					case TCMain.addButId:	
						prod.setId(lastId+1);
						productDAO.add(prod);
						unpop();
						break;
					case TCMain.updateButId:
						prod.setId(product.getId());
						productDAO.update(prod);
						unpop();
						break;
					case TCMain.deleteButId:
						productDAO.delete(product.getId());
						unpop();
						break;
				}
		}	

	}
}

package Database;

import java.util.ArrayList;

import Model.Product;
import litebase.LitebaseConnection;
import litebase.PreparedStatement;
import litebase.ResultSet;

public class ProductDAO {
	private LitebaseConnection connection = LitebaseConnection.getInstance("APPI");;
	
	public ProductDAO() {		
		if(!connection.exists("product")){
			connection.execute("create table product "
					+ "(id int primary key not null,"
					+ "name char(20) not null,"
					+ "description char(100) not null)");
		}
	}
	
	public void setPreparedStatement(PreparedStatement pStat,Product product){
	
		pStat.setInt(0, product.getId());
		pStat.setString(1, product.getName());
		pStat.setString(2, product.getDescription());
	}
	
	public void add(Product product){
		String query = "insert into product values(?,?,?)";
		
		PreparedStatement pStat = connection.prepareStatement(query);
		setPreparedStatement(pStat,product);
		
		pStat.executeUpdate();
	}
	
	public ArrayList<Product> getAll(){
		String query = " select * from product";		
	
		PreparedStatement pStat = connection.prepareStatement(query);
		
		ResultSet result = pStat.executeQuery();
		
		ArrayList<Product> products = new ArrayList<Product>();
		while(result.next()){
			Product pro = new Product();
			pro.setId(result.getInt("id"));
			pro.setName(result.getString("name"));
			pro.setDescription(result.getString("description"));
			
			products.add(pro);
		}
		
		result.close();
		
		return products;
	}
	
	public void update(Product product){
		PreparedStatement pStat = connection.prepareStatement("update product set name = ?, description = ? where id = ?");
		
		pStat.setInt(2, product.getId());
		pStat.setString(0, product.getName());
		pStat.setString(1, product.getDescription());
		
		
		pStat.executeUpdate();
	}
	
	public void delete(int id){
		
		PreparedStatement pStat = connection.prepareStatement("delete product where id = ? ");
		
		pStat.setInt(0, id);
		
		pStat.executeUpdate();
	}
}

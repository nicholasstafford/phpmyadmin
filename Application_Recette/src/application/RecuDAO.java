package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecuDAO {
	
	public static void insertRecu(String objet, String destination, double prix, double tax, double total) throws ClassNotFoundException, SQLException
	{
		String sql="insert into recu(objet, destination, prix, tax, total) values('"+objet+"','"+destination+"',"+prix+","+tax+","+total+")";
		try
		{ 
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur à l'insertion de données " + e);
			e.printStackTrace();
			throw e;
		}
	}
	public static void updateRecu(int ID, String objet, String destination, double prix, double tax, double total) throws ClassNotFoundException, SQLException
	{
		String sql="update recu set objet='"+objet+"', destination='"+destination+"', tax="+tax+", prix="+prix+", total="+total+" where ID= "+ ID;
		
		try
		{
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la mise à jour");
			e.printStackTrace();
			throw e;
		}
		
	}
	public static void deleteRecetteById(int ID) throws ClassNotFoundException, SQLException
	{
		String sql="delete from recu where ID= "+ ID;
		try
		{
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la suppression de données");
			e.printStackTrace();
			throw e;
		}
	}
	public static ObservableList<Recette> getAllRecords() throws ClassNotFoundException, SQLException
	{
		String sql="select * from recu";
		try
		{
			ResultSet rsSet=DBUtilitaires.dbExecute(sql);
			
			
			ObservableList<Recette> RecetteList=getRecetteObjects(rsSet);
			return RecetteList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la recupération de données à afficher"+e);
			e.printStackTrace();
			throw e;
		}
		
	}
	private static ObservableList<Recette> getRecetteObjects(ResultSet rsSet) throws ClassNotFoundException, SQLException
	{
		try
		{
			
			ObservableList<Recette> RecetteList=FXCollections.observableArrayList();
			while(rsSet.next())
			{
				Recette recu=new Recette();
				recu.setID(rsSet.getInt("ID"));
				recu.setDestination(rsSet.getString("destination"));
				recu.setTax(rsSet.getDouble("tax"));
				recu.setPrix(rsSet.getDouble("prix"));
				recu.setObjet(rsSet.getString("objet"));
				recu.setTotal(rsSet.getDouble("total"));
				RecetteList.add(recu);
			}
			return RecetteList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur au moment de l'affichage de données "+ e);
			e.printStackTrace();
			throw e;
		}
	}
	public static ObservableList<Recette> searchRecette(String recuId) throws ClassNotFoundException, SQLException
	{
		String sql="select * from recu where ID="+recuId;
		try
		{
		 ResultSet rsSet=DBUtilitaires.dbExecute(sql)	;
		 ObservableList<Recette> list=getRecetteObjects(rsSet);
		 return list;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur pendant la recherche de données " +e);
			e.printStackTrace();
			throw e;
		}
	}	
	
	
	
	
	
	
	

}

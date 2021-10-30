package application;

public class Recette 
{
	private String objet;
	private String destination;
	private Double tax;
	private Double prix;
	private Double total;
	private int ID;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Recette()
	{
		this(null,null);
	}
	
	public Recette(String destination, String objet)
	{
		this.objet = "";
		this.destination = "";
		this.tax = 0.0;
		this.prix = 0.0;
		this.total = 0.0;
	}
	
	public String getObjet()
	{
		return objet;
	}
	
	public void setObjet(String objet)
	{
		this.objet = objet;
	}
	
	public String getDestination()
	{
		return destination;
	}
	
	public void setDestination(String destination)
	{
		this.destination = destination;
	}
	
	public Double getTax()
	{
		return tax;
	}
	
	public void setTax(Double tax)
	{
		this.tax = tax;
	}
	
	public Double getPrix()
	{
		return prix;
	}
	
	public void setPrix(Double prix)
	{
		this.prix = prix;
	}
	
	public Double getTotal()
	{
		return total;
	}
	
	public void setTotal(Double total)
	{
		this.total = total;
	}

}

package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "recettes")
public class RecetteListWrapper
{
		private List<Recette> recettes;
		@XmlElement(name = "recette")
		public List<Recette> getRecettes()
		{
			return recettes;
		}
		
		public void setRecettes(List<Recette> recettes)
		{
				this.recettes = recettes;
		}
}	

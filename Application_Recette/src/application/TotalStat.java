package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class TotalStat 
{
	@FXML
	private BarChart<String, Integer> barChart;
	
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> intervalTotals = FXCollections.observableArrayList();
	
	@FXML
	private void initialize()
	{
		intervalTotals.add("0-10");
		intervalTotals.add("10-20");
		intervalTotals.add("20-30");
		intervalTotals.add("30-40");
		intervalTotals.add("40-50");
		intervalTotals.add("50-60");
		xAxis.setCategories(intervalTotals);
	}
	
	public void SetStats(List<Recette> recettes)
	{
		int[] totalCounter = new int[6];
		
		for(Recette e:recettes)
		{
			double total = e.getTotal();
			
			if(total<=10)
				totalCounter[0]++;
				else
					if(total<=20)
						totalCounter[1]++;
						else
							if(total<=30)
								totalCounter[2]++;
							else
								if(total<=40)
									totalCounter[3]++;
								else
										if(total<=50)
											totalCounter[4]++;
										else
											totalCounter[5]++;
		}
		XYChart.Series<String,Integer> series = new XYChart.Series<>();
		series.setName("totales");
		
		for(int i = 0; i<totalCounter.length; i++)
		{
			series.getData().add(new XYChart.Data<>(intervalTotals.get(i), totalCounter[i]));
		}
		barChart.getData().add(series);
	}
	
}

package Temp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProgramOne
{
	public static void main(String[] args) throws IOException
	{
		Document doc;
		Map<String, List<String>> topCategoryToLabels = new LinkedHashMap<String, List<String>>();   // use to store category to label mappings
		Map<String, List<String>> labelToProduct = new LinkedHashMap<String, List<String>>();		// use to store label to product mappings

		doc = Jsoup.connect("http://www.snapdeal.com/page/sitemap").get();
		Set<String> categories = new LinkedHashSet<String>();          // set of all the categories
		Elements top = doc.select("div[id=SMWrapr]");                 // extract elements with div id = "SMWrapr"
		Elements elements = top.select("div[class=SmBox1]");         // extract elements with div class = "SmBox1"
		evaluateCategories(elements, categories);
		
		for(String tab:categories)
		{
			elements = top.select("div[id="+tab+"]");
			Elements head = elements.select("div[class=SmBox_Head]");    // extract elements with div class = "SmBox_Head"
			String str = head.select("span[class=TAlgn3]").text();      // extract value of elements with span class="TAlgn3"
			String subCat = "";
			Elements subElements = elements.select("div[class=ht180]"); // extract elements with div class = "ht180"
			List<String> catHead = new ArrayList<String>();             // list of the labels for particular category  
			for(Element subElementCat: subElements)
			{
				String subCatHead = getCategoryHead(subElementCat);
				List<String> productList;                             // list of product list for particular sub label
				if(!subCatHead.equals(""))
				{
					subCat = subCatHead;                              
					catHead.add(subCat);
					productList = new ArrayList<String>();
				}
				else
				{
					productList = labelToProduct.get(subCat);       // if present sub label is empty, use previous one
				}		
				updateProductList(subElementCat, productList, labelToProduct, subCat);
			}
			topCategoryToLabels.put(str, catHead);               // update category to label entry
		}
		
		printOutputToConsole(topCategoryToLabels, labelToProduct);
	}

	private static void updateProductList(Element subElementCat,
			List<String> productList, Map<String, List<String>> labelToProduct, String subCat)
	{
		Elements products = subElementCat.select("li a");
		for(Element product : products)
		{
			productList.add(product.text());               // final product list
		}
		labelToProduct.put(subCat, productList);          // update label to product mapping
	}

	/*
	 * return category head
	 */
	private static String getCategoryHead(Element subElementCategory)
	{
		String subCatHead = subElementCategory.select("div[class=SMSubcatHead]").select("b").text();  // list of sub labels
		if(subCatHead.equals(""))
		{
			subCatHead = subElementCategory.select("div[class=SMSubcatHead]").select("a").text();
		}
		return subCatHead;
	}

	/*
	 * print output to console
	 */
	private static void printOutputToConsole(
			Map<String, List<String>> topCategoryToLabels,
			Map<String, List<String>> labelToProduct)
	{
		Iterator<Entry<String,List<String>>> entryIterator = topCategoryToLabels.entrySet().iterator();
		while(entryIterator.hasNext())
		{
			Entry<String,List<String>> entry= entryIterator.next();
			String categoryEntry = entry.getKey();
			List<String> listLabel = entry.getValue();
			System.out.println(categoryEntry);
			for(String label: listLabel)
			{
				System.out.println();
				System.out.print(label+ " : ");
				List<String> prodList = labelToProduct.get(label);
				Iterator<String> prodIterator  = prodList.iterator();
				while(prodIterator.hasNext())
				{
					System.out.print(prodIterator.next());
					if(prodIterator.hasNext())
					{
						System.out.print(",");
					}
				}
			}
			if(entryIterator.hasNext())
			{
				System.out.println();
				System.out.println();
				System.out.println();
			}
		}
	}

	/*
	 * evaluate list of categories from the elements and update category set
	 */
	private static void evaluateCategories(Elements elements,
			Set<String> categories)
	{
		for(Element element :elements)
		{
			Elements elementList = element.select("a[href]");
			for(Element e:elementList)
			{
				categories.add(e.attr("href").toString().replace("#", ""));
			}
		}
	}
}
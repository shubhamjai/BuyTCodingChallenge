import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RoundOneProgramOne {
	static void generateProductMap(Map<String, Set<String>> prod, String file)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		while ((str = br.readLine()) != null) {
			if(str.isEmpty()) continue;
			String arr[] = str.split("\\|");
			int arrLength = arr.length;
			Set<String> val = null;
			if (arrLength == 2) {
				val = prod.get("home");             // add root of  the tree
				if (val == null) {
					val = new HashSet<String>();
				}
				val.add(arr[0].trim());
				prod.put("home", val);
			}
			val = prod.get(arr[arrLength - 2].trim());
			if (val == null) {
				val = new HashSet<String>();
			}
			val.add(arr[arrLength - 1].trim());
			prod.put(arr[arrLength - 2].trim(), val);
		}
	}

	public static void main(String[] args) throws IOException {
		Map<String, Set<String>> products = new LinkedHashMap<String, Set<String>>();
		//Provide the absolute file path
		String fileName = "Enter Absolute File Path";    // e.g. C:\\shubham\\Buyt\\Input.txt
		generateProductMap(products, fileName);
		for (String str : products.keySet()) {
			System.out.print(str + " contains   ");
			int length = products.get(str).size();
			System.out.print("[");
			int i = 0;
			for (String child : products.get(str)) {
				System.out.print(child);
				if (i++ < length - 1) {
					System.out.print(" , ");
				}
			}
			System.out.println("]");
		}
	}
}
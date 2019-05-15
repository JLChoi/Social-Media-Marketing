//Tiffany Moi
//tmoi
//HW03


import java.net.*;    
import java.util.*;
import java.io.*; 
import java.util.regex.*;

public class Wikipedia {
	
	
	public String getHTML(String urlName) {
		try {

		URLConnection url = new URL(urlName).openConnection();
		
		Scanner scanner = new Scanner(url.getInputStream());
		
		String html = "";

		while (scanner.hasNext()) {
				
			String next = scanner.next();
				
			html += next + " ";

		}
			
			scanner.close();
			return html;
		}

		catch (MalformedURLException e) {

			return "Malformed URL: " + urlName;

		}

		catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}
	
	public List<String> awardsRegex(String line, String query){
		
		List<String> ret = new ArrayList<String>();
		String template = "<table class=\"wikitable\">(.*)</table>"; 
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(line);
		String movieAwards = ""; //Gets information from the awards table
		
		if (m.find()) {
			movieAwards = m.group(1);
		}
		
		
		//Getting actual table data now
		Map<String, ArrayList<String>> awards = new TreeMap<String, ArrayList<String>>();
		String catTemplate = ".*\">(.*?)</a></b></div> <ul><li>(.*?)\">(.*?)</a>(.*?)</td>"; 
		//String winnerTemplate = ".*</a></b></div> <ul><li><b>.*>(.*?)</a>";
		
		Pattern catP = Pattern.compile(catTemplate); // create a Pattern object 
		//Pattern winnerP = Pattern.compile(winnerTemplate);
		
		Matcher catM = catP.matcher(movieAwards); // create matcher object 
		//Matcher winnerM = winnerP.matcher(movieAwards);
				
		while (catM.find()) { // perform matching 
			String awardCat = catM.group(1);
			//String[] strArr = catM.group(2).split(">");
			//String awardWinner = strArr[strArr.length-1];
			String awardWinner = catM.group(3);
			
			String nominations = catM.group(4);
			//System.out.println(nominations);
			
			//System.out.println();
			
			if (nominations.contains(query) || awardWinner.contains(query)) {			
				ret.add(awardCat);
			}
			
			if (awards.containsKey(awardWinner)) {
				awards.get(awardWinner).add(awardCat);
			}
			else {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(awardCat);
				awards.put(awardWinner, arr);
			}
			
			catM = catP.matcher(movieAwards.substring(0,catM.start(1)));
			
		} 
		return ret;
	}
	
	public List<String> nominatedMovies(String line, int query) {
		List<String> ret = new ArrayList<String>();
		String start = "Films with multiple nominations(.*?)</table>";
		Pattern p = Pattern.compile(start);
		Matcher m = p.matcher(line);
		String tableData = "";
		
		if (m.find()) {
			tableData = m.group(1);
		}
		
		int numAwards = 0; 
		String movies = "";
		
		//Different first template to handle the last row
		String template = ".*style=\"text-align:center\">(\\d*)(.*?)</tbody";
		p = Pattern.compile(template);
		m = p.matcher(tableData);
		
		while (m.find()) {
			numAwards = Integer.parseInt(m.group(1));
			movies = m.group(2);
			
			//System.out.println(numAwards);
			//System.out.println(movies);
			//System.out.println();
			
			if (numAwards >= query) {
				String innerMovies = "<a href.*>(.*?)</a>";
				Pattern pInner = Pattern.compile(innerMovies);
				Matcher mInner = pInner.matcher(movies);
				while (mInner.find()) {
					//System.out.println(mInner.group(1));
					ret.add(mInner.group(1));
					mInner = pInner.matcher(movies.substring(0,mInner.start(1)));
				}
			}
			
			//Change template to reflect different sections of the table with different
			//number of nominations.
			template = ".*style=\"text-align:center\">(\\d*)(.*?)<td (rowspan|scope)"; 
			p = Pattern.compile(template);
			m = p.matcher(tableData.substring(0,m.start(1)));
		}
		return ret;
	}
	
	public List<String> awardCat(String line, String query) {
		
		List<String> ret = new ArrayList<String>();
		String template = ".*<a href=\"(.*?)>" + query + "</a></b></div> <ul><li>";
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(line);
		
		String link = "https://en.wikipedia.org";
		
		if (m.find()) {
			link += m.group(1).split("\"")[0];
		}
		else {
			return ret;
		}
		
		//System.out.println(link);
		
		String html = getHTML(link);
		//System.out.println(html);
		
		template = "<table class=\"wikitable sortable\"(.*?)<th scope=\"row\"(.*?)>(.*?)</a>(.*?)</td>";
		p = Pattern.compile(template);
		m = p.matcher(html);
		
		if (m.find()) {
			
			String year = m.group(3).split(">")[1];
			String inner = m.group(4);
			
			//System.out.println(year);
			
			template = "<td style=\"background:#FAEB86.*>(.*?)</a></span>";
			p = Pattern.compile(template);
			m = p.matcher(inner);
			String winner = "";
			
			ret.add(year);
			
			if (m.find()) {
				winner = m.group(1);
			}
			ret.add(winner);
		}
		//System.out.println(ret);
		return ret;
	}
	
	public List<String> money(String line, String query) {
		
		List<String> ret = new ArrayList<String>();
		
		String template = ".*\">" + query + "</a></b></div> <ul><li>(.*?)<a href=\"(.*?)\" title"; 
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(line);
		
		String link = "https://en.wikipedia.org";
		
		if (m.find()) {
			String awardWinner = m.group(2);
			//System.out.println(awardWinner);
			link += m.group(2);
		}
		else {
			return ret;
		}
		
		String html = getHTML(link);
		
		template = ".*Budget</th><td>(.*?)<.*Box office</th><td>(.*?)<";
		p = Pattern.compile(template);
		m = p.matcher(html);
		
		if (m.find()) {
			ret.add(m.group(1));
			ret.add(m.group(2));
		}
		
		//System.out.println(ret);
		return ret;
		
	}
	
	public String runtime(String line, String query) {
		
		
		String template = "<table class=\"wikitable\">(.*)</table>"; 
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(line);
		String movieAwards = ""; //Gets information from the awards table
		
		if (m.find()) {
			movieAwards = m.group(1);
		}
		
		String base = "https://en.wikipedia.org";
		
		//Getting actual table data now
		String catTemplate = ".*\">"+query+"</a></b></div> <ul><li>(.*?)</td>"; 
		
		Pattern catP = Pattern.compile(catTemplate); // create a Pattern object 
		
		Matcher catM = catP.matcher(movieAwards); // create matcher object 
		
		List<ArrayList<String>> movies = new ArrayList<ArrayList<String>>();
				
		if (catM.find()) { // perform matching 
			String awardCat = catM.group(1);
			
			String innerTemplate = ".*<a href=\"(.*?)\" title.*>(.*?)</a>.*–";
			Pattern innerP = Pattern.compile(innerTemplate);
			Matcher innerM = innerP.matcher(awardCat);
			while (innerM.find()) {
				String link = base + innerM.group(1);
				String movie = innerM.group(2);
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(movie);

				String html = getHTML(link);
				//System.out.println(link);
				
				template = ".*Running time</div></th><td>(.*?) min";
				p = Pattern.compile(template);
				m = p.matcher(html);
				
				if (m.find()) {
					arr.add(m.group(1));
				}
				movies.add(arr);
				//System.out.println(arr);
				innerM = innerP.matcher(awardCat.substring(0,innerM.start(1)));
			}
			
		}
		
		int min = 100000000;
		String ret = "";
		
		for (int i = 0; i < movies.size(); i ++) {
			int runtime = Integer.parseInt(movies.get(i).get(1));
			if (runtime < min) {
				ret = movies.get(i).get(0);
				min = runtime;
			}
		}
		//System.out.println(ret);
		return ret;
		
	}

	public List<String> companies(String line, String query) {
		
		String q = "";
		String innerTemplate = "";
		
		if (query == "distributed") {
			q = "Distributed by";
			innerTemplate = ".*"+q+"(.*?)<a(.*?)title=\"(.*?)\"";
		} else {
			q = "Production<br />company";
			innerTemplate = ".*"+q+" </div>(.*?)</div>";
			//System.out.println(innerTemplate);
		}
		
		String start = "Films with multiple nominations(.*?)</table>";
		Pattern p = Pattern.compile(start);
		Matcher m = p.matcher(line);
		String tableData = "";
		
		if (m.find()) {
			tableData = m.group(1);
		}
		
		//Different first template to handle the last row
		String template = ".*<td><i><a href=\"(.*?)\"";
		p = Pattern.compile(template);
		m = p.matcher(tableData);
		
		String base = "https://en.wikipedia.org";
		Map<String, Integer> companies = new TreeMap<String, Integer>();
		
		while (m.find()) {
				
			String link = base + m.group(1);
			//System.out.println(link);

			String html = getHTML(link);
			
			Pattern innerP = Pattern.compile(innerTemplate);
			Matcher innerM = innerP.matcher(html);
			String company = "";
			
			if (innerM.find()) {
				if (q == "Distributed by") {
					company = innerM.group(3);
					if (companies.containsKey(company)) {
						companies.put(company, companies.get(company) + 1);
					}
					else {
						companies.put(company, 1);
					}
				} 
				else {
					String producers = innerM.group(1);
					String pTemp = ".*<li>(.*?)<";
					Pattern pPatt = Pattern.compile(pTemp);
					Matcher pMatch = pPatt.matcher(producers);
					
					while (pMatch.find()) {

						company = pMatch.group(1);
						String[] arr = company.split(">");
						if (arr.length > 1) {
							company = arr[1].split("<")[0];
						}

						if (companies.containsKey(company)) {
							companies.put(company, companies.get(company) + 1);
						}
						else {
							companies.put(company, 1);
						}
						pMatch = pPatt.matcher(producers.substring(0,pMatch.start(1)));
					}
				}
				
			}
		
				
//			template = ".*Running time</div></th><td>(.*?) min";
//			p = Pattern.compile(template);
//			m = p.matcher(html);
//				
//			if (m.find()) {
//				arr.add(m.group(1));
//			}
//			movies.add(arr);
//			System.out.println(arr);
//			innerM = innerP.matcher(awardCat.substring(0,innerM.start(1)));
//				
//			mInner = pInner.matcher(movies.substring(0,mInner.start(1)));
			m = p.matcher(tableData.substring(0,m.start(1)));
		}
		
		int max = 0;
		List<String> ret = new ArrayList<String>();
			
		for (Map.Entry<String,Integer> entry : companies.entrySet()) { 
			if (entry.getValue() == max) {
				ret.add(entry.getKey());
			}
			if (entry.getValue() > max) {
				max = entry.getValue();
				ret.clear();
				ret.add(entry.getKey());
			} 
        
		}
		
		System.out.println(ret);
		return ret;	
	}
	
	public Map<String, Integer> countries(String line, String query) {
		
		Map<String, Integer> countryCount = new HashMap<String, Integer>();
		
		String template = ".*<a href=\"(.*?)\">"+query+"</a></b></div> <ul><li>(.*?)</td>";
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(line);
		
		String base = "https://en.wikipedia.org";
		
		String awardLink = "";
		String nominees = "";
		
		if (m.find()) {
			awardLink = m.group(1).split("\"")[0];
			System.out.println(awardLink);
			nominees = m.group(2);
		}
		else {
			return countryCount;
		}
		
		//Finding all relevant countries
		//System.out.println(nominees);
		Set<String> targetCountries = new TreeSet<String>();
		String innerTemplate = ".*<i><a href=\"(/wiki.*?)\" title.*>(.*?)</a>.*–";
		Pattern innerP = Pattern.compile(innerTemplate);
		Matcher innerM = innerP.matcher(nominees);
		while (innerM.find()) {
			String link = base + innerM.group(1);

			String html = getHTML(link);
			//System.out.println(link);
				
			template = ".*Country</th><td>(.*?)</td>";
			p = Pattern.compile(template);
			m = p.matcher(html);
				
			if (m.find()) {
				String innerText = m.group(1);
				if (innerText.contains("<")) {
					String temp = ".*<li>(.*?)<";
					Pattern pCountries = Pattern.compile(temp);
					Matcher mCountries = pCountries.matcher(innerText);
					while (mCountries.find()) {
						targetCountries.add(mCountries.group(1));
						mCountries = pCountries.matcher(innerText.substring(0,mCountries.start(1)));
					}
				}
				else {
					targetCountries.add(innerText);
				}
			}
			innerM = innerP.matcher(nominees.substring(0,innerM.start(1)));
			
		}
		
		if (query.contains("Foreign")) {
			String foreignLink = "https://en.wikipedia.org/wiki/List_of_countries_by_number_of_Academy_Awards_for_Best_Foreign_Language_Film";
			String foreignHTML = getHTML(foreignLink); 
			//System.out.println(foreignHTML);
			for (String country : targetCountries) {
				String cTemp = "\">" + country + "</a>(.*?)</tr>";
				Pattern cPat = Pattern.compile(cTemp);
				Matcher cMatch = cPat.matcher(foreignHTML);
				
				if (cMatch.find()) {
					String inner = cMatch.group(1);
					cTemp = ".*\\+</span>(.*?) </td>";
					cPat = Pattern.compile(cTemp);
					cMatch = cPat.matcher(inner);
					List<Integer> l = new ArrayList<Integer>();
					
					while (cMatch.find()) {
						l.add(Integer.parseInt(cMatch.group(1)));
						cMatch = cPat.matcher(inner.substring(0,cMatch.start(1)));
					}
					
					if (l.size() > 1) {
						countryCount.put(country, l.get(1));
					}
				}
			}
		}
		else {
			// Scrape award page
			
			String html = getHTML(base + awardLink);
			
			template = ".*table class=\"wikitable\"(.*?)</table>";
			p = Pattern.compile(template);
			m = p.matcher(html);
			
			List<String> tableData = new ArrayList<String>();
			
			while (m.find()) {
				//System.out.println(m.group(1));
				tableData.add(m.group(1));
				m = p.matcher(html.substring(0, m.start(1)));
			}
			
			//System.out.println(tableData.size());
			
			for (int i = 0; i < tableData.size(); i ++) {
			
				//Go through all nominees and get their countries
				String movieTemp = "<td><i>(.*?)<a href=\"(/wiki.*?)\"";
				//System.out.println(movieTemp);
				Pattern pMovies = Pattern.compile(movieTemp);
				Matcher mMovies = pMovies.matcher(tableData.get(i));
				
				while (mMovies.find()) {
					
					//System.out.println(mMovies.group(2));
					String movieHTML = getHTML(base + mMovies.group(2));
					
					
					String countryTemp = ".*Country</th><td>(.*?)</td>";
					Pattern pCountry = Pattern.compile(countryTemp);
					Matcher mCountry = pCountry.matcher(movieHTML);
						
					if (mCountry.find()) {
						String innerText = mCountry.group(1);
						if (innerText.contains("<")) {
							String temp = ".*<li>(.*?)<";
							Pattern pCountries = Pattern.compile(temp);
							Matcher mCountries = pCountries.matcher(innerText);
							while (mCountries.find()) {
								String country = mCountries.group(1);
								
								if (targetCountries.contains(country)) {
									if (countryCount.containsKey(country)) {
										countryCount.put(country, countryCount.get(country) + 1);
									}
									else {
										countryCount.put(country, 1);
									}
								}
								mCountries = pCountries.matcher(innerText.substring(0,mCountries.start(1)));
								//System.out.println(country);
								//System.out.println(countryCount.get(country));
							}
						}
						else {
							if (targetCountries.contains(innerText)) {
								if (countryCount.containsKey(innerText)) {
									countryCount.put(innerText, countryCount.get(innerText) + 1);
								}
								else {
									countryCount.put(innerText, 1);
								}
							}
							//System.out.println(innerText);
							//System.out.println(countryCount.get(innerText));
						}
					}
	
				}
				
			}
		}
		
		return countryCount;
	}
	
	public List<String> multipleAwards(String line, int num, String category) {
		
		List<String> ret = new ArrayList<String>();
		
		String template = ".*<a href=\"(.*?)\">"+category+"</a></b></div> <ul><li>";
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(line);
		
		String base = "https://en.wikipedia.org";
		
		String awardLink = "";
		
		if (m.find()) {
			awardLink = m.group(1).split("\"")[0];
		}
		else {
			return ret;
		}
		
		String awardHTML = getHTML(base+awardLink);
		
		template = "<tbody><tr> <th scope=\"col\" width=\"55\">Nominations(.*?)</table>";
		p = Pattern.compile(template);
		m = p.matcher(awardHTML);
		String tableData = "";
		
		if (m.find() ) {
			tableData = m.group(1);
		}
		
		//System.out.println(tableData);
		
		template = ".*<td( rowspan|><center>| style=\"text-align:center\">)(.*?)</td>(.*)</tbody>";
		p = Pattern.compile(template);
		m = p.matcher(tableData);
		
		while (m.find()) {
			String containsNum = m.group(2);
			//System.out.println("CONTIN: " + containsNum);
			int numAwards = 0;
			if (containsNum.contains("</center>")) {
				numAwards = Integer.parseInt(containsNum.replace("</center> ", ""));
					
			} 
			else if (containsNum.contains(">")){
				numAwards = Integer.parseInt(containsNum.split(">")[1].replace(" ", ""));
			} 
			else {
				numAwards = Integer.parseInt(containsNum.replace(" ", ""));
			}
			String people = m.group(3);
			
			//System.out.println(numAwards);
			//System.out.println(people);
			//System.out.println();
			
			if (numAwards >= num) {
				String innerPeople = ".*<td>(.*?) </td></tr>";
				Pattern pInner = Pattern.compile(innerPeople);
				Matcher mInner = pInner.matcher(people);
				while (mInner.find()) {
					//System.out.println(mInner.group(1));
					ret.add(mInner.group(1));
					mInner = pInner.matcher(people.substring(0,mInner.start(1)));
				}
			}
			
			//Change template to reflect different sections of the table with different
			//number of nominations.
			template = ".*<td( rowspan|><center>| style=\"text-align:center\">)(.*?)</td>(.*)<td( rowspan|><center>| style=\"text-align:center\">)"; 
			p = Pattern.compile(template);
			m = p.matcher(tableData.substring(0,m.start(1) + 30));
		}
		
		return ret;
	}
	public static void main(String[] args) throws IOException {
		
		Wikipedia w = new Wikipedia();
		
		String url = "https://en.wikipedia.org/wiki/91st_Academy_Awards";
		String html = w.getHTML(url);
		
		//System.out.println(w.awardsRegex(html, "Alfonso Cuarón"));
		
		//System.out.println(w.nominatedMovies(html, 3));
		
		//System.out.println(w.awardsRegex(html));
		//w.awardCat(html, "Best Picture");
		
		
		//w.money(html, "Best Original Screenplay");
		//w.runtime(html, "Best Documentary – Feature");
		
		//w.companies(html, "distributed");
		
//		Map<String, Integer> result = w.countries(html, "Best Foreign Language Film");
//		
//		for (Map.Entry<String,Integer> entry : result.entrySet()) {
//			System.out.println(entry.getKey() + ": " + entry.getValue());
//		}
		
		//System.out.println(w.multipleAwards(html, 2, "Best Actress"));
//		United Kingdom: 2
//		Lebanon: 2
//		Japan: 16
//		Poland: 11
//		Mexico: 9
//		United Kingdom: 2
//		France: 39
//		Germany: 11
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("What would you like to know?\n");
		String q1 = "1: How many awards was __ nominated for? List them.";
		String q2 = "2: List all the movies that were nominated for at least __ awards.";
		String q3 = "3: When was the award for __ first awarded? Who won the award that year?";
		String q4 = "4: What was the budget for the __ winner? How much did this movie make in the box office?";
		String q5 = "5: Which of the nominees for __ has the shortest running time?";
		String q6 = "6: Which company __ the most films that received multiple nominations?";
		String q7 = "7: For __ , for the countries that were nominated/won, how many times have they been nominated in the past (including this year)?";
		String q8 = "8: Who has been nominated for more than __ awards for __ ?";
		
		String questions = q1 + "\n" + q2 + "\n" + q3 + "\n" + q4 + "\n" + q5 + "\n" + q6 + "\n" +  q7 + "\n" + q8 + "\n"; 
		
		System.out.println(questions);
		System.out.println("Please choose one and type the number followed by ; and the arguments with no spaces in between.\n(i.e) 1;Alfonso Cuarón\nNote that the last one needs 3 arguments.");
		
		while (scanner.hasNext()) {
			
			String input = scanner.nextLine();
			
			String[] inputs = input.split(";");
			
			int selection = Integer.parseInt(inputs[0]);
			
			if (selection == 1) {
				System.out.println(w.awardsRegex(html, inputs[1]));
			}
			
			if (selection == 2) {
				System.out.println(w.nominatedMovies(html, Integer.parseInt(inputs[1])));
				
			}
			
			if (selection == 3) {
				System.out.println(w.awardCat(html, inputs[1]));
				
			}
			
			if (selection == 4) {
				System.out.println(w.money(html, inputs[1]));
				
			}
			
			if (selection == 5) {
				System.out.println(w.runtime(html, inputs[1]));
				
			}
		
			if (selection == 6) {
				System.out.println(w.companies(html, inputs[1]));
				
			}
			
			if (selection == 7) {
				
				Map<String, Integer> result = w.countries(html, inputs[1]);
				
				for (Map.Entry<String,Integer> entry : result.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}
				
			}
			
			if (selection == 8) {
				System.out.println(w.multipleAwards(html, Integer.parseInt(inputs[1]), inputs[2]));
				
			}
			
			System.out.println("\n\n");
			System.out.println(questions);
			System.out.println("Please choose one and type the number.");
		}

		
		
	}

}

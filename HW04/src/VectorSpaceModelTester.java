import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * the tester class.
 * @author swapneel
 */
public class VectorSpaceModelTester {

	public static void main(String[] args) {
		
		Document d1 = new Document("mysterious-affair.txt");
		Document d2 = new Document("murder-links.txt");
		Document d3 = new Document("secret-adversary.txt");
		Document d4 = new Document("anna-karenina.txt");
		Document d5 = new Document("war-peace.txt");
		Document d6 = new Document("short-stories.txt");
		
		ArrayList<Document> documentsAgatha = new ArrayList<Document>();
		documentsAgatha.add(d1);
		documentsAgatha.add(d2);
		documentsAgatha.add(d3);
		
		Corpus corpus = new Corpus(documentsAgatha);
		
		VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
		
//		for (int i = 0; i < documents.size(); i++) {
//			for (int j = i + 1; j < documents.size(); j++) {
//				Document doc1 = documents.get(i);
//				Document doc2 = documents.get(j);
//				System.out.println("\nComparing " + doc1 + " and " + doc2);
//				System.out.println(vectorSpace.cosineSimilarity(doc1, doc2));
//			}
//		}
		
		for(int i = 1; i < documentsAgatha.size(); i++) {
			Document doc = documentsAgatha.get(i);
			System.out.println("\n" + d1 + " comparing to " + doc);
			System.out.println(vectorSpace.cosineSimilarity(d1, doc));
		}
		
		for(int i = 2; i < documentsAgatha.size(); i++) {
			Document doc = documentsAgatha.get(i);
			System.out.println("\n" + d2 + " comparing to " + doc);
			System.out.println(vectorSpace.cosineSimilarity(d2, doc));
		}
		
		ArrayList<Document> documentsTolstoy = new ArrayList<Document>();
		documentsTolstoy.add(d4);
		documentsTolstoy.add(d5);
		documentsTolstoy.add(d6);
		
		Corpus corpusT = new Corpus(documentsTolstoy);
		
		VectorSpaceModel vectorSpaceT = new VectorSpaceModel(corpusT);
		
		for(int i = 1; i < documentsTolstoy.size(); i++) {
			Document doc = documentsTolstoy.get(i);
			System.out.println("\n" + d4 + " comparing to " + doc);
			System.out.println(vectorSpaceT.cosineSimilarity(d4, doc));
		}
		
		for(int i = 2; i < documentsTolstoy.size(); i++) {
			Document doc = documentsTolstoy.get(i);
			System.out.println("\n" + d5 + " comparing to " + doc);
			System.out.println(vectorSpaceT.cosineSimilarity(d5, doc));
		}
		
		
		
	}

}

package platform.util;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import platform.social.vo.SocialNews;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBClient {

	private static MongoDBClient instance;
	
	private static MongoClient client;
	private static Morphia morphiaInstance;
	
	private MongoDBClient(){}
	
	public MongoClient getClient() {
		return client;
	}
	
	public DB getDB(String dbname){
		return client.getDB(dbname);
	}
	
	public Datastore getDataStore(String dbname){
		return morphiaInstance.createDatastore(client, dbname); 
	}
	
	public Morphia getMorphiaInstance() {
		return morphiaInstance;
	}

	public static MongoDBClient getInstance(){
		if(instance==null){
			synchronized(MongoDBClient.class){
				if(instance==null){
					instance = new MongoDBClient();
					client = commonTool.base.MongoDBClient.getInstance().getClient();
					morphiaInstance = new Morphia();
					morphiaInstance.map(SocialNews.class);
				}
			}
		}
		return instance;
	}
	
}

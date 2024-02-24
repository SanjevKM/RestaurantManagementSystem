package Interface;

import java.sql.Connection;

public interface Searchable {
	
      void searchByCategory(Connection connection, int category);
      void searchByPrice(Connection connection,double minprice,double maxprice);
}
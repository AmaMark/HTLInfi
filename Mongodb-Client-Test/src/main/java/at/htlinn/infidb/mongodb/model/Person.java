package at.htlinn.infidb.mongodb.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;

public class Person {

	public static final List<String> names = Arrays.asList( "Micha", "Tim", "Andy", "Lili", "Barbara", "Clemens", "Merten", "Marius");
	public static final List<String> hobbies = Arrays.asList( "Sport", "Reisen", "Musik", "TV", "Kino", "Wandern", "Fotografieren");
	
	static class Address {
		public final String state; public final int zipCode; public final String city;
		
		public Address(final String state, final int zipCode, final String city) {
			this.state = state; this.zipCode = zipCode; this.city = city;
		}
	}
	
	public static final List<Address> addresses = Arrays.asList(new Address("CH", 8952, "Schlieren")
			, new Address("DE", 24106, "Kiel")
			, new Address("CH", 8003, "Zürich")
			, new Address("DE", 52070, "Aachen")
			, new Address("A", 1133, "Wien"));
	
	private static Document createPersonDocument(final String name) {
		final Document document = new Document();
		document.append("name", name);
		document.append("age", 1 + (int) (Math.random() * 100)); document.append("insertionDate", new Date()); 
		document.append("hobbies", Arrays.asList(createHobbiesArray())); 
		document.append("address", createAddressDocument());
		return document;
	}
	
	private static String[] createHobbiesArray() {
		final int maxNrOfHobbies = 1 + (int) (Math.random() * (Person.hobbies.size() / 2));
		return Person.hobbies.stream().filter(item -> Math.random() > 0.5).limit(maxNrOfHobbies).toArray(String[]::new);
	}
	
	private static Document createAddressDocument() {
		final int random = (int) (Math.random() * Person.addresses.size());
		final Person.Address address = Person.addresses.get(random);
		final Document document = new Document();
			document.append("country", address.state);
			document.append("zipCode", address.zipCode);
			document.append("city", address.city);
		return document;
	}
}

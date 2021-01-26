package at.htl.onlinebanking.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Kunde
 *
 */
@Entity
@Table(name = "OBKunde", schema = "ONLINEBANKING",
uniqueConstraints=@UniqueConstraint(columnNames="KUNDNR"))
public class Kunde implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length=36)
	private String id;
	
	@Column(name="KUNDNR",length=36)
	private String kundennummer;
	@Column(name="VORNAME")
	private String vorname;
	@Column(name="NACHNAME")
	private String nachname;
	@Column(name = "DATNEU")
	private LocalDate dateOfCreation;
	@OneToMany(mappedBy = "kunde", fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Konto> konten;
	
	public Set<Konto> getKonten() {
		return konten;
	}

	public void setKonten(Set<Konto> konten) {
		this.konten = konten;
	}

	public String getKundennummer() {
		return kundennummer;
	}

	public void setKundennummer(String kundennummer) {
		this.kundennummer = kundennummer;
	}
	
	protected Kunde() {
		id = UUID.randomUUID().toString();
		dateOfCreation = LocalDate.now();
		konten = new HashSet<>();
	}
	
	public String getId() {
		return id;
	}

	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String toString() {
		return "id=[" + id + "],vorname = [" + vorname + "],nachname=[" + nachname + "]";
	}
	
	public void addKonto(Konto k) {
		k.setKunde(this);
		konten.add(k);
	}
	
	public LocalDate getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(LocalDate dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
    public static KundeBuilder builder() {
        return new KundeBuilder();
    }
    
	public static class KundeBuilder {
		private String kundennummer;
		private String vorname;
		private String nachname;
		
        public KundeBuilder setKundennummer(final String kundennummer) {
            this.kundennummer = kundennummer;
            return this;
        }
        public KundeBuilder setVorname(final String vorname) {
            this.vorname = vorname;
            return this;
        }
        public KundeBuilder setNachname(final String nachname) {
            this.nachname = nachname;
            return this;
        }
       
        public Kunde build() {
        	Kunde kunde = new Kunde();
        	kunde.setKundennummer(kundennummer);
        	kunde.setVorname(vorname);
        	kunde.setNachname(nachname);
            return kunde;
        }
	}
}

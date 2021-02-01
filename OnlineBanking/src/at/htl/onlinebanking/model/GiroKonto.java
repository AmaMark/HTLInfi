package at.htl.onlinebanking.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import at.htl.onlinebanking.model.Kunde.KundeBuilder;


@Entity
@DiscriminatorValue("GIRO")
public class GiroKonto extends Konto {
	
	@Column(precision = 5, scale = 3)
	private BigDecimal zinssatzHaben;
	@Column(precision = 5, scale = 3)
	private BigDecimal zinssatzSoll;
	@Column(precision = 10, scale = 2)
	private BigDecimal rahmen;
	
	public BigDecimal getZinssatzHaben() {
		return zinssatzHaben;
	}
	public void setZinssatzHaben(BigDecimal zinssatzHaben) {
		this.zinssatzHaben = zinssatzHaben;
	}
	public BigDecimal getZinssatzSoll() {
		return zinssatzSoll;
	}
	public void setZinssatzSoll(BigDecimal zinssatzSoll) {
		this.zinssatzSoll = zinssatzSoll;
	}
	public BigDecimal getRahmen() {
		return rahmen;
	}
	public void setRahmen(BigDecimal rahmen) {
		this.rahmen = rahmen;
	}

	protected GiroKonto() {
		super();
	}
	
    public static GiroKontoBuilder builder() {
        return new GiroKontoBuilder();
    }
    
	public static class GiroKontoBuilder {
		private BigInteger kontonummer;
		private String kontobezeichnung;
	
        public GiroKontoBuilder setKontonummer(final BigInteger kontonummer) {
            this.kontonummer = kontonummer;
            return this;
        }
        
        public GiroKontoBuilder setKontobezeichnung(final String kontobezeichnung) {
            this.kontobezeichnung = kontobezeichnung;
            return this;
        }
        
		private BigDecimal zinssatzHaben;
		private BigDecimal zinssatzSoll;
		private BigDecimal rahmen;

        public GiroKontoBuilder setZinssatzHaben(final BigDecimal zinssatzHaben) {
            this.zinssatzHaben = zinssatzHaben;
            return this;
        }
        
        public GiroKontoBuilder setZinssatzSoll(final BigDecimal zinssatzSoll) {
            this.zinssatzSoll = zinssatzSoll;
            return this;
        }
        
        public GiroKontoBuilder setRahmen(final BigDecimal rahmen) {
            this.rahmen = rahmen;
            return this;
        }
       
        public GiroKonto build() {
        	GiroKonto konto = new GiroKonto();
        	konto.setKontonummer(kontonummer);
        	konto.setKontobezeichnung(kontobezeichnung);
        	
        	konto.setZinssatzHaben(zinssatzHaben);
        	konto.setZinssatzSoll(zinssatzSoll);
        	konto.setRahmen(rahmen);

            return konto;
        }
	}
}

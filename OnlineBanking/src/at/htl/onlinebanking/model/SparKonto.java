package at.htl.onlinebanking.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import at.htl.onlinebanking.model.GiroKonto.GiroKontoBuilder;

@Entity
@DiscriminatorValue("SPAR")
public class SparKonto extends Konto {
	private BigDecimal zinssatzHaben;
	
	public BigDecimal getZinssatzHaben() {
		return zinssatzHaben;
	}

	public void setZinssatzHaben(BigDecimal zinssatzHaben) {
		this.zinssatzHaben = zinssatzHaben;
	}
	
	protected SparKonto() {
		super();
	}
	
    public static SparKontoBuilder builder() {
        return new SparKontoBuilder();
    }
    
	public static class SparKontoBuilder {
		private BigInteger kontonummer;
		private String kontobezeichnung;
	
        public SparKontoBuilder setKontonummer(final BigInteger kontonummer) {
            this.kontonummer = kontonummer;
            return this;
        }
        
        public SparKontoBuilder setKontobezeichnung(final String kontobezeichnung) {
            this.kontobezeichnung = kontobezeichnung;
            return this;
        }
        
		private BigDecimal zinssatzHaben;

        public SparKontoBuilder setZinssatzHaben(final BigDecimal zinssatzHaben) {
            this.zinssatzHaben = zinssatzHaben;
            return this;
        }
       
        public SparKonto build() {
        	SparKonto konto = new SparKonto();
        	konto.setKontonummer(kontonummer);
        	konto.setKontobezeichnung(kontobezeichnung);
        	
        	konto.setZinssatzHaben(zinssatzHaben);
            return konto;
        }
	}
}

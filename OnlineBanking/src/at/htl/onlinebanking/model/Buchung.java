package at.htl.onlinebanking.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "OBBuchung", schema = "ONLINEBANKING", 
uniqueConstraints=@UniqueConstraint(columnNames="NUMMER"))

public class Buchung implements Serializable {
	private static final long serialVersionUID = -5297985541831525081L;
	@Id
	private String id;
	
//	@TableGenerator(
//		    table="GENERATOR_TABLE",
//		    pkColumnName = "key",
//		    valueColumnName = "next",
//		    pkColumnValue="order_number",
//		    allocationSize=30)
//	@GeneratedValue(strategy=GenerationType.TABLE, generator="orderGenerator")
	private Integer nummer;
	
	
	private String buchungsText;
	public String getBuchungsText() {
		return buchungsText;
	}
	public void setBuchungsText(String buchungsText) {
		this.buchungsText = buchungsText;
	}

	private LocalDate valutaDatum;
	public LocalDate getValutaDatum() {
		return valutaDatum;
	}
	public void setValutaDatum(LocalDate valutaDatum) {
		this.valutaDatum = valutaDatum;
	}

	private LocalDate buchungsDatum;
	public LocalDate getBuchungsDatum() {
		return buchungsDatum;
	}
	public void setBuchungsDatum(LocalDate buchungsDatum) {
		this.buchungsDatum = buchungsDatum;
	}

	@Column(precision = 10, scale = 2)
	private BigDecimal betrag;
	public BigDecimal getBetrag() {
		return betrag;
	}
	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}
	
	@Enumerated(EnumType.STRING)
	private Buchungsart art;
	
	public Buchungsart getArt() {
		return art;
	}
	public void setArt(Buchungsart art) {
		this.art = art;
	}
	
	@Enumerated(EnumType.STRING)
	private BuchungsTyp buchungstyp;

	public BuchungsTyp getBuchungstyp() {
		return buchungstyp;
	}
	public void setBuchungstyp(BuchungsTyp buchungstyp) {
		this.buchungstyp = buchungstyp;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "konto", nullable = false)
	private Konto konto;
	public Konto getKonto() {
		return konto;
	}
	public void setKonto(Konto konto) {
		this.konto = konto;
	}

	public Buchung() {
		id = UUID.randomUUID().toString();
		valutaDatum = LocalDate.now();
		buchungsDatum = LocalDate.now();
		buchungstyp = BuchungsTyp.BAR;
		konto = null;
	}
	public Buchung(BigDecimal b, Buchungsart a, String t) {
		super();
		buchungsText = t;
		betrag = b;
		art = a;
	}
	public Integer getNummer() {
		return nummer;
	}
	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}
}

package at.htl.onlinebanking.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.Size;

import at.htl.onlinebanking.model.GiroKonto.GiroKontoBuilder;
import at.htl.onlinebanking.model.Kunde.KundeBuilder;


/**
 * Entity implementation class for Entity: Konto
 *
 */
@Entity
@Table(name = "OBKONTO", schema = "ONLINEBANKING",
uniqueConstraints=@UniqueConstraint(columnNames="KONTONR"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "KontoTyp",discriminatorType = DiscriminatorType.STRING)

public abstract class Konto {
	@Id
	@Column(length=36)
	private String id;
	
	@Column(name="KONTONR", nullable= false, precision=11, scale=0)
	private BigInteger kontonummer;
	
	@Size(max = 80)
	@Column(name="DISPNAME")
	private String kontobezeichnung;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal saldo;
	
	@Column(name = "DATNEU")
	private LocalDate dateOfCreation;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "kunde", nullable = false)
	private Kunde kunde;
	
	@OneToMany(mappedBy = "konto", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<Buchung> buchungen;
	
	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public BigInteger getKontonummer() {
		return kontonummer;
	}
	
	public void setKontonummer(BigInteger k) {
		kontonummer = k;
	}
	
	public String getKontobezeichnung() {
		return kontobezeichnung;
	}
	public void setKontobezeichnung(String k) {
		kontobezeichnung = k;
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}
	
	public void setSaldo(BigDecimal s) {
		saldo = s;
	}
	
	Konto() {
		this.id = UUID.randomUUID().toString();
		kontobezeichnung = "Konto";
		saldo = new BigDecimal(0);
		dateOfCreation = LocalDate.now();
	}
	
	public void addBuchug(Buchung b) {
		b.setKonto(this);
		buchungen.add(b);
	}
}

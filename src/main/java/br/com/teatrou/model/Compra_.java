package br.com.teatrou.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ {

	public static volatile SingularAttribute<Compra, Long> codigo;
	public static volatile SingularAttribute<Compra, Integer> quantidadeIngresso;
	public static volatile SingularAttribute<Compra, BigDecimal> valorTotal;
	public static volatile SingularAttribute<Compra, Usuario> usuario;
	public static volatile SingularAttribute<Compra, LocalDate> dataCompra;

}

package br.com.teatrou.model;

import br.com.teatrou.model.enums.SituacaoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ {

	public static volatile SingularAttribute<Compra, String> codigo;
	public static volatile SingularAttribute<Compra, SituacaoEnum> situacao;
	public static volatile SingularAttribute<Compra, Integer> quantidadeIngresso;
	public static volatile SingularAttribute<Compra, BigDecimal> valorTotal;
	public static volatile SingularAttribute<Compra, Usuario> usuario;
	public static volatile SingularAttribute<Compra, LocalDate> dataCompra;

}


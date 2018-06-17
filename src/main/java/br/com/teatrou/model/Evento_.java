package br.com.teatrou.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Evento.class)
public abstract class Evento_ {

	public static volatile SingularAttribute<Evento, BigDecimal> valorIngresso;
	public static volatile SingularAttribute<Evento, String> horaInicial;
	public static volatile SingularAttribute<Evento, Long> codigo;
	public static volatile SingularAttribute<Evento, Boolean> ativo;
	public static volatile SingularAttribute<Evento, String> endereco;
	public static volatile SingularAttribute<Evento, String> anexo;
	public static volatile SingularAttribute<Evento, String> horaFinal;
	public static volatile SingularAttribute<Evento, String> titulo;
	public static volatile SingularAttribute<Evento, String> descricao;
	public static volatile SingularAttribute<Evento, LocalDate> dataEvento;
	public static volatile SingularAttribute<Evento, String> tema;
	public static volatile SingularAttribute<Evento, Integer> quantidadeIngresso;
	public static volatile SingularAttribute<Evento, Usuario> usuario;

}


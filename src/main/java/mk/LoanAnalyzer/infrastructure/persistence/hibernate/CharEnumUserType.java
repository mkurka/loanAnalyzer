package mk.LoanAnalyzer.infrastructure.persistence.hibernate;

import mk.LoanAnalyzer.domain.ICharIdEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Persists enum identified by char. Inherit me with specific class.
 * 
 * @author fja
 *
 * @param <E> Enum type.
 */
public abstract class CharEnumUserType<E extends Enum<E>> implements UserType<E> {

	private final Class<E> clazz;
	

	protected CharEnumUserType(Class<E> c) {
		this.clazz = c;
	}
	

	private static final int[] SQL_TYPES = { Types.CHAR };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return clazz;
	}

	@Override
	public E nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
		String id = rs.getString(position);
		E result = null;
		if (!rs.wasNull()) {
			result = getValue(id.charAt(0));
		}
		return result;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, E value, int index,
							SharedSessionContractImplementor session) throws HibernateException, SQLException {
		Object obj = value;
		if (null == value) {
			st.setNull(index, Types.CHAR);
		} else if (obj instanceof String) {
			// Fixme je potreba i v ESport?
			// Pri kombinaci enum column a data source request mi prijde primo hodnota, tak ji pouziji.
			st.setString(index, (String)obj);
		} else {
			st.setString(index, Character.toString(((ICharIdEnum) value).getId()));
		}
	}

	public E deepCopy(E value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public E assemble(Serializable cached, Object owner)
			throws HibernateException {
		return (E) cached;
	}

	public Serializable disassemble(E value) throws HibernateException {
		return (Serializable) value;
	}

	public E replace(E original, E target, Object owner)
			throws HibernateException {
		return original;
	}

	public int hashCode(E x) throws HibernateException {
		return x.hashCode();
	}

	public boolean equals(E x, E y) throws HibernateException {
		if (x == y)
			return true;
		if (null == x || null == y)
			return false;
		return x.equals(y);
	}

	@Override
	public int getSqlType() {
		return SqlTypes.CHAR;
	}

	public abstract E getValue(char id);
}

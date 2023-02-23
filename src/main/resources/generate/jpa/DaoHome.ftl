<#assign package = pojo.getPackageDeclaration()>
${package?substring(0,package?length-1)}.daos;
// Generated ${date} by Hibernate Tools ${version}

//import org.springframework.data.repository.CrudRepository;
<#if clazz.identifierProperty??>import ${c2j.getJavaTypeName(clazz.identifierProperty, jdk5)};</#if>
import ${pojo.getQualifiedDeclarationName()};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Dao object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
<#if ejb3>
@${pojo.importType("javax.ejb.Stateless")}
</#if>
<#if clazz.identifierProperty??>
public interface ${declarationName}JpaDao 
	extends JpaRepository<${declarationName}, ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)}>, QuerydslPredicateExecutor<${declarationName}>
{
}
<#else>
public interface ${declarationName}JpaDao {
}
</#if>
</#assign>
${pojo.generateImports()}
${classbody}
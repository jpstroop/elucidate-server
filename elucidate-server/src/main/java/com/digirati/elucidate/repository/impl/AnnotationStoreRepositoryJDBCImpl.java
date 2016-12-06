package com.digirati.elucidate.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.digirati.elucidate.common.infrastructure.rowmapper.W3CAnnotationRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.repository.AnnotationStoreRepository;

@Repository
public class AnnotationStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationStoreRepository {

    public static final String REPOSITORY_NAME = "annotationStoreRepositoryJdbcImpl";

    @Autowired
    public AnnotationStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public W3CAnnotation getAnnotationByCollectionIdAndAnnotationId(String collectionId, String annotationId) {
        String sql = "SELECT * FROM annotation_get WHERE collectionid = ? AND annotationid = ? AND deleted = ?";
        Object[] params = {collectionId, annotationId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    public List<W3CAnnotation> getAnnotationsByCollectionId(String collectionId) {
        String sql = "SELECT * FROM annotation_get WHERE collectionid = ? AND deleted = ?";
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    public W3CAnnotation createAnnotation(String collectionId, String annotationId, String annotationJson) {
        String sql = "SELECT * FROM annotation_create(?, ?, ?)";
        Object[] params = {collectionId, annotationId, annotationJson};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    public W3CAnnotation updateAnnotation(String collectionId, String annotationId, String annotationJson) {
        String sql = "SELECT * FROM annotation_update(?, ?, ?)";
        Object[] params = {collectionId, annotationId, annotationJson};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    public W3CAnnotation deleteAnnotation(String collectionId, String annotationId) {
        String sql = "SELECT * FROM annotation_delete(?, ?)";
        Object[] params = {collectionId, annotationId};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    public int countDeletedAnnotations(String collectionId, String annotationId) {
        String sql = "SELECT COUNT(1) FROM annotation_get WHERE collectionid = ? AND annotationid = ? AND deleted = ?";
        Object[] params = {collectionId, annotationId, true};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        return queryForClass(sql, params, sqlTypes, Integer.class);
    }
}

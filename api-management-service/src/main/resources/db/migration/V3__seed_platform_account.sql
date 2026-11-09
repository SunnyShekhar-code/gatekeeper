INSERT INTO management_users (organization_id, role_id, username, password_hash, enabled, created_at, updated_at)
         SELECT NULL, roles.id,'platform-admin',
        '$2a$12$JX3GUBKyb3PTx7AtD.qg8.6Edi4aZ2G5xiGAPMycMbMwo9FCSg.Za',
         TRUE,CURRENT_TIMESTAMP , CURRENT_TIMESTAMP FROM roles where roles.name='PLATFORM_ADMIN';
// platform123

INSERT INTO management_users (organization_id, role_id, username, password_hash, enabled, created_at, updated_at)
         SELECT NULL, roles.id,'gateway-service',
        '$2a$12$6gu5sgK3ZWbQ2eFYtZ4Mue7kDk8IHTC5pe3bMs3gAm5Fj.k3D/E1y',
         TRUE,CURRENT_TIMESTAMP , CURRENT_TIMESTAMP FROM roles where roles.name='GATEWAY_SERVICE';
//service123
# README #

### Issue: Nextcloud redirects to localhost

Solution:
Open the config.php (/mnt/raid1/applications/cloud/config/config.php) and add:

```
'overwritehost' => 'cloud.rickiekarp.net',
'overwriteprotocol' => 'https',
```

Source: https://docs.nextcloud.com/server/15/admin_manual/configuration_server/config_sample_php_parameters.html#proxy-configurations
```
Proxy Configurations
'overwritehost' => '',
The automatic hostname detection of Nextcloud can fail in certain reverse proxy and CLI/cron situations. This option allows you to manually override the automatic detection; for example www.example.com, or specify the port www.example.com:8080.

'overwriteprotocol' => '',
When generating URLs, Nextcloud attempts to detect whether the server is accessed via https or http. However, if Nextcloud is behind a proxy and the proxy handles the https calls, Nextcloud would not know that ssl is in use, which would result in incorrect URLs being generated.

Valid values are http and https.
```
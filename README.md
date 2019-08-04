xmlns:app="http://schemas.android.com/apk/res-auto"
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>

network_security_config


// Anko Commons
    api "com.facebook.fresco:fresco:$fresco_version"
    api "org.jetbrains.anko:anko-commons:$anko_version"
    api "com.github.nickgao1986:NicksBaseLib:$baselib_version"

    api "io.reactivex.rxjava2:rxandroid:$rxjava2_version"
    api "io.reactivex.rxjava2:rxjava:$rxjava2_version"
    api "com.tbruyelle.rxpermissions2:rxpermissions:$rxpermission_version"

    //Alert View
    api "com.bigkoo:alertview:${alert_view_version}"
    
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<provider
                android:name="android.support.v4.content.FileProvider"
                android:authorities="${applicationId}.fileprovider"
                android:exported="false"
                android:grantUriPermissions="true">
            <meta-data
                    android:name="android.support.FILE_PROVIDER_PATHS"
                    android:resource="@xml/provider_paths" />
        </provider>
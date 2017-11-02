package com.alelk.bcpt.storage;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Bcpt Storage Config
 *
 * Created by Alex Elkin on 02.11.2017.
 */
@Configuration
@ComponentScan
@PropertySource({"bcpt-storage.properties"})
class BcptStorageConfig {


}

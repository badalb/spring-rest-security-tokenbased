CREATE  TABLE IF NOT EXISTS `tblhuenckey` (
 `encrypt_ID` char(38) NOT NULL,
 `appID` varchar(50) NOT NULL,
  `access_key` varchar(50) NOT NULL,
  `encrypt_key` varchar(256) NOT NULL,
  `hmac_key` varchar(256) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `is_deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`encrypt_id`),
  UNIQUE KEY `access_key_UNIQUE` (`access_key`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
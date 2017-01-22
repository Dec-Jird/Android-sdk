package com.iapppay.demo.activity;

public class PayConfig {
	
	/**
	 * appid		-- 应用appid
	 * privateKey   -- 应用私钥
	 * publicKey	-- 平台公钥(platKey)
	 * notifyurl	-- 商户服务端接收支付结果通知的地址
	 * */


	/**线上环境*/
	public static final String appid = "3001259821";
	public static final String privateKey = "MIICXgIBAAKBgQCOqWFTCsTglunf036QBof9EnKo4g6EaZ+IYa+u3jMf3p4YeHmOZjvE8jTxJ+tLvVfroRJNIRQX81uJKhTbtfkBxQjOiC8y9pyfQXz02WTl6DFIM9Kgjjx51bGnEKP8ploW2ieqKCDBzYCwkVEQfr7ayOPx0WWD0Cw3J6nb3EDUKQIDAQABAoGBAI440BzQdJuN99Q67Ua6LCIgnQw+aMia3/8/m7xSKleQQL4WhOBwjQ93g04TROC5/4eZiTw5SOXjp5Kj0C2FSZp5lmIggAg/KRgFhcdQ28jxmGzBD9+2cshTucYoa7YxtaeuljODT21nh7m5XF9HEngU7Ty8nnW8Byumpxa5bYCBAkEA1d3sSsjR7Y5wV6a9VgZsOKvExoCeUSjSaC6/KAZmJHExZB2Mb4yh+3LSV4Pi9xnz4umk/JFWRMJ7oQs7JsOVUQJBAKrEUf9Dbtm1qzvUy474e83N6+3iUsX8p5giRSahaOSsl7jCnnwqwF8nhh2I40XliH106nC4YV2c3f7cS8BEe1kCQQCY319OPapBgrWvEdL5MPIeuDmaIsoH/YQZUID3nUtZ9Ud25uBBxGbtFDBiujV8qCJ7KsPyffkKgXJZtWt81AVhAkEAnoiHvz0xKfiYMYGKQP66oQOtJjlYsumuBXS7UfPDV5hLeoFjdM6TrUMaJU0yAW/oWOAzzdW+vpOlHLgTszlgcQJAPSHsnZBSlKPJUHt3D7tGedgi6f8ayrbqxlsbvMQEFVzgUr0x1OV4ni+5oZOsP6jfoRLeWbiS8GMZlp2t326fgA==" ;
	public static final String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPJmrO2n5qNNfiXM1hAUJlcOL95J2MZ8FJI9HYmD+GuFn9K75KFNEjdc3ZaRRmjSW6GA1ZLJ2Ddgf8XWjXZ5g1sO5y6zQWrq9OZpk6plKjAKF8LEfJyS7zClOG/ziUNj164GShMWOTmJ0xdLX6wTG/QYQ4DLu43gi1Y+RZbwkSGQIDAQAB";
	public static final String notifyurl = "http://192.168.0.140:8094/monizhuang/api?type=100";
	
}

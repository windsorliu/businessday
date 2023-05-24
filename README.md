## 簡介

在銀行業務中，許多操作都與「營業日」密切相關，而不同國家對於營業日的定義也有所不同。
此專案可以檢索並獲取 **特定貨幣** 和 **年份** 的營業日資料。

## 環境與技術

* Spring Boot: 2.3.7.RELEASE
* Java JDK 11
* Spring Boot Validation
* org.json
* Lombok

## API

輸入參數
|  參數名稱  |    格式    |  範例  |
|:------:|:----------:| :-----------: |
|  年份(year)  | 四碼西元年(yyyy)  | 2016 |
| 幣別(currency)  |   三碼英文(不分大小寫)   | twd |
<br>

**查詢該年份(year)的所有貨幣列表**
```
GET:  localhost:8080\businessday\{year}
```
e.g.  想要取得2018年的所有貨幣代碼<br>
`\businessday\2018`
<br>
<br>
![demo video](https://github.com/windsorliu/businessday/blob/main/images/getCurrencyList.gif)
[Demo video](https://github.com/windsorliu/businessday/blob/main/images/getCurrencyList.gif)
<br>
<br>
<br>
<br>
**查詢特定貨幣(currency)在特定年份(year)的營業日資訊**
```
GET:  localhost:8080\businessday?year=yyyy&currency=xxx
```
e.g.  想要取得2016年貨幣代碼為TWD的營業日資訊<br>
`\businessday?year=2016&currency=twd`
<br>
<br>
![demo video](https://github.com/windsorliu/businessday/blob/main/images/getResults.gif)
[Demo video](https://github.com/windsorliu/businessday/blob/main/images/getResults.gif)
<br>
<br>
如果要取得多個貨幣在多個年份的營業日資訊，請用 "," 區隔開參數<br>
e.g.  想要取得2016,2019年貨幣代碼為TWD,USD的營業日資訊<br>
`\businessday?year=2016,2019&currency=twd,usd`
<br>
<br>
![demo video](https://github.com/windsorliu/businessday/blob/main/images/getMultipleResults.gif)
[Demo video](https://github.com/windsorliu/businessday/blob/main/images/getMultipleResults.gif)
<br>

## 資料

[資料來源檔案](https://github.com/windsorliu/businessday/tree/main/src/main/resources/static)

資料來源檔案欄位說明
|  欄位名稱  |  中文名稱  |  說明  |
|:------:|:----------:| :-----------: |
| Currency  | 幣別     |  |
| holidays  | 國定假日 |  |
| weekends  | 例假日   | 0為週日，1為周六，以此類推。 |
| workdays  | 補班日   |  |

>備註：若workdays無資料，內容為空字串；反之內容為Array格式。


## 例外處理

程式會檢查傳入參數內容，若不符合規範則以log紀錄錯誤訊息。

錯誤訊息依序分為兩類：
* 找不到檔案(年份輸入錯誤)
* 找不到幣別<br><br>

![demo video](https://github.com/windsorliu/businessday/blob/main/images/exception.gif)
[Demo video](https://github.com/windsorliu/businessday/blob/main/images/exception.gif)
<br>
>備註：因為來源檔案目前只有2016年至2020年，若年份不在這個區間則被視為**年份輸入錯誤**
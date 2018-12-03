# Business Card - Entities Extraction
A quick project to demonstrate extraction of meaningful entities from business cards using Azure Cognitive Services.

Readme Article
https://www.linkedin.com/pulse/business-cards-entity-extraction-using-azures-services-pankaj-khattar/

This is java project illustrating how to extract entities from a Business Card, A Business Card can contain entities like Name, Address, Phone Number, Email & Organization.

Now in order to extract the above information out of business cards, we will follow the below steps:
1) Convert the Business card to Image by taking photos of the card
2) Use Azure's Computer Vision API to detect Text from Images, The API uses optical character recognition (OCR) and extract the recognized words into a machine-readable character stream.
3) Use Azure's Entity Recognition API onto the extracted Text to extract Entities with links to more information on the web (Wikipedia and Bing)

The web services for the above api's are exposed at following end points on azure cloud:
1) Image to Text : https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/ocr
2) Text to Entities : https://westus.api.cognitive.microsoft.com/text/analytics/v2.1-preview/entities

User require to pass subscription keys for individial services along with required keys to get the information.

The sample Java project does the above steps & can be executed by invoking the following code

java com.ms.sample.card.entity.EntityExtractManager <Image-path> <subscription-key-OCR> <subscription-key-Entities>

The resultant output looks something like 

Image = IMG_20181123_184344.jpg
Email = Jaspal.Singh@Veeam.com
Organization = Veeam Software
Phone/Quantity_NumberRange = 91-9999140860
Person = Jaspal Singh
URL = www.veeam.com
Location = New Delhi, India


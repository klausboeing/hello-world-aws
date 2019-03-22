# hello-world-aws

Aditional Config
======================================
- S3 Event

AfterUploadEvent

prefix: source/
SendoTo: Lambda Function
Lambda: hello-world-aws-dev-AfterUploadEventHandler

- DynamoStream

hello-world-aws-dev-AfterCopyEventHandler

Limitations
===============
create notifications existent bucket
- https://github.com/serverless/serverless/issues/4284
- https://github.com/serverless/serverless/issues/2717

create stage variables
- https://forum.serverless.com/t/cant-set-api-gateway-stagevariables/314/4
- https://github.com/serverless/serverless/issues/1455

create stage variables plugin bug
- https://github.com/leftclickben/serverless-api-stage

Reference
===============
- https://www.youtube.com/watch?v=1j7USsviHpg warmup plugin
- https://www.stardog.com/blog/5-hard-lessons-about-serverless/
- https://serverless-stack.com/
- https://forum.serverless.com/t/cloudformation-cannot-update-a-stack-when-a-custom-named-resource-requires-replacing/3217/4 "Cannot update a stack when a custom-named resource requires replacing"
- https://stackoverflow.com/questions/49154292/dynamodb-with-boolean-attribute-using-aws-sdk-java - use number not boolean (mapping)
- https://docs.aws.amazon.com/pt_br/lambda/latest/dg/dlq.html - dead letter queues lambda
- https://forum.serverless.com/t/dynamodb-streams-creation/792/2 - Mapping dynamodb as stream
- https://stackoverflow.com/questions/31789078/aws-dynamodb-trigger-using-lambda-in-java - dynamo streams
- https://aws.amazon.com/pt/blogs/database/how-to-perform-ordered-data-replication-between-applications-by-using-amazon-dynamodb-streams/ - DynamoStreams com lambda
- https://stackoverflow.com/questions/37614286/aws-s3-add-set-update-specify-user-metadata-with-a-presigned-url - metadata presign url
- https://docs.aws.amazon.com/pt_br/lambda/latest/dg/best-practices.html
- https://stackoverflow.com/questions/38342418/outofmemoryerror-when-creating-amazons3client-in-lambda
- https://aws.amazon.com/pt/blogs/developer/the-dynamodbmapper-local-secondary-indexes-and-you/
- https://aws.amazon.com/pt/blogs/database/choosing-the-right-dynamodb-partition-key/
- https://lobster1234.github.io/2017/04/12/serverless-framework-aws-apigateway/

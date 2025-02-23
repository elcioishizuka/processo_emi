provider "aws" {
  region = "sa-east-1"
}

resource "aws_dynamodb_table" "debit_status_change" {
  name         = "DebitStatusChange"
  billing_mode = "PAY_PER_REQUEST" 

  hash_key  = "customerId"
  range_key = "automaticDebitId"

  attribute {
    name = "customerId"
    type = "S" 
  }

  attribute {
    name = "automaticDebitId"
    type = "S" 
  }

  tags = {
    Environment = "dev"
    Terraform   = "true"
  }
}

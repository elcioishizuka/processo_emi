provider "aws" {
  region = "sa-east-1"
}

resource "aws_sqs_queue" "debit_queue" {
  name                      = "debit-queue"
  delay_seconds             = 0
  max_message_size          = 262144
  message_retention_seconds = 604800 # Retenção de 7 dias
  receive_wait_time_seconds = 0
  visibility_timeout_seconds = 30
  
  tags = {
    Environment = "dev"
    Application = "auto-debit"
  }
}

output "queue_url" {
  value = aws_sqs_queue.debit_queue.id
}

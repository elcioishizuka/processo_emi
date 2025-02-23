provider "aws" {
  region = "sa-east-1"
}

resource "aws_api_gateway_rest_api" "auto_debit_api" {
  name        = "AutomaticDebitAPIProxy"
  description = "Automatic Debit Proxy API Gateway"
}

resource "aws_api_gateway_resource" "proxy_resource" {
  rest_api_id = aws_api_gateway_rest_api.auto_debit_api.id
  parent_id   = aws_api_gateway_rest_api.auto_debit_api.root_resource_id
  path_part   = "{proxy+}"
}

resource "aws_api_gateway_method" "proxy_method" {
  rest_api_id   = aws_api_gateway_rest_api.auto_debit_api.id
  resource_id   = aws_api_gateway_resource.proxy_resource.id
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "proxy_integration" {
  rest_api_id             = aws_api_gateway_rest_api.auto_debit_api.id
  resource_id             = aws_api_gateway_resource.proxy_resource.id
  http_method             = aws_api_gateway_method.proxy_method.http_method
  type                    = "HTTP_PROXY"
  uri                     = "http://hostname.com/{proxy}" # Substituir pelo hostname do API

  request_parameters = {
    "integration.request.path.proxy" : "method.request.path.proxy"
  }
}

resource "aws_api_gateway_deployment" "deployment" {
  depends_on = [aws_api_gateway_integration.proxy_integration]

  rest_api_id = aws_api_gateway_rest_api.auto_debit_api.id
}

resource "aws_api_gateway_stage" "stage" {
  rest_api_id = aws_api_gateway_rest_api.auto_debit_api.id
  deployment_id = aws_api_gateway_deployment.deployment.id
  stage_name  = "dev"
}

output "api_url" {
  value = aws_api_gateway_deployment.deployment.invoke_url
}
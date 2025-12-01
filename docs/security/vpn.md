# VPN and Proxy Considerations

Some security features rely on the client's IP address.

## Details

- Rate limiting and logging use the IP address from the request.
- If users are behind a VPN or proxy, the IP may not reflect the real client.
- The `X-Forwarded-For` header is checked to obtain the original IP when available.

## Recommendation

- If deploying behind a proxy or load balancer, ensure it forwards the `X-Forwarded-For` header.

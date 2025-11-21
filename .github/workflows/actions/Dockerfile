FROM alpine:latest
RUN apk add --no-cache bash
COPY rickroll.sh /rickroll.sh
RUN chmod +x /rickroll.sh
ENTRYPOINT ["/rickroll.sh"]

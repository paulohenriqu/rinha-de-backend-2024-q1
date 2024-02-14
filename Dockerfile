FROM ubuntu:jammy
COPY target/rinha /rinha
CMD ["/rinha"]
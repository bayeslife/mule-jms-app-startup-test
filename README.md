# mule-jms-app-startup-test
This maven project demonstrates a startup problem with Mule 3.7.3 and the JMS Connector.

The blocking=false option on the jms:activemq-connector does not appear to operate as per mule documentation  [on the mule web site](https://docs.mulesoft.com/mule-user-guide/v/3.6/configuring-reconnection-strategies)


This is a simple project with a mule context which includes a jms connector.
The connection is designed to point to a non-default port where active mq is not running.

When 'mvn test' is run the unit test never finishes.
Pointing to a valid port number with active mq running, the unit test runs successfully.
This demonstrates that the jms connection is non-blocking appears not to be running.



```
<jms:activemq-connector name="Active_MQ"
  durable="true" connectionFactory-ref="AmqConnectionFactory" username="admin"
  password="password" disableTemporaryReplyToDestinations="true"
    persistentDelivery="true"
  doc:name="Active MQ" specification="1.1" validateConnections="true"  numberOfConsumers="1" maxRedelivery="1" clientId="client1">
  <reconnect-forever blocking="false"/>
</jms:activemq-connector>
```

The connectionFactory-ref points to a connection definition for active mq running on a non-default port

```
<spring:bean name="AmqConnectionFactory"
  class="org.apache.activemq.spring.ActiveMQConnectionFactory"
  p:brokerURL="failover:(tcp://localhost:61617)" p:redeliveryPolicy-ref="AmqRedeliveryPolicy" />
</spring:beans>
```

Any suggestions on how to enable the non-blocking functionality would be appreciated.

Mulesoft Support
```
Hi Phil,

Also i was able to run your unit test by changing the brokerurl to tcp://localhost:61617.

For blocking="true" the test case was having below result
Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 60.398 sec <<< FAILURE!

For blocking="false" the test case was having below failure
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS


Thanks
Ram
```
```
Hello Phil,

Is this just an issue in running a unit test?

When using the JMS failover option, it is slightly different to when not using it. In that within the same connection attempt, the JMS driver will attempt to reconnect to the listed IP's in the failover url. This is a feature of ActiveMQ and the JMS Driver. However each failure is not a "connection failure" until you cannot get ANY connection to any of the listed IPs. Only in this situation is the reconnection strategy invoked.

Why is blocking="false" essential for your test? As Ram indicated, the usage of this attribute is to allow an application to deploy successfully to Mule Server, when the JMS broker is known to be down or unavailable. It is also useful to allow your application to be deployed if other applications have some dependancy on it.

However for running a unique unit test, it should not be a problem do either not use a failover url, or not use blocking - for that test.

Cheers,
```


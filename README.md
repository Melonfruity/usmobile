### Business Assumptions
- Definitions
  - BillingCycle (Cycle): Period of time in which a customer is active (may or may not be using the service)
  - Daily Usage: a record of which keeps track of data usage of a customer and a unique mdn within an active service
  - User: A customer record
- Customer can have multiple MDNs (different lines)
  - Can potentially have different billing cycles and data usages for each customer
- Phone number should not be shared between multiple customers
- What should happen if a phone number is transfered from user A to B? I am assuming that the old billing cycle will become invalid and that the user model or a model not introduced yet (such as a MDN model) should keep track of what own the MDN or if it owns the user

### Business Improvements
- Business Plan
  - Consider allowing customers (e.g., corporate accounts) to open and manage phone numbers for others, such as employees. This can be part of a business-oriented plan where an administrator handles billing and usage for multiple users under one account.

### Technical Assumptions
- Cycle-usage service can potentially involve writing the usage data to the database
- "Assume that the usage collection is updated every 15 minutes for the usedInMb field." I am assuming that the cycle-usage service does not updates the values, so the Cycle Usage Service only provides analytics.
- "50 million documents are updated every 15m" - This should imply that Indexing is required since the data changes often
- "JWT is trustworthy" - The service is within a protected system or the cloud such as within a VPC - The JWT can be provided many ways... I will assume as a part of the Auth Header

### Technical Improvements
- Introduce a MDN model: The mdn can have its own history, which service provider owns it and so on.
- Rate Limiting: If needed (this can prevent some internal bugs or intentional / un intentional abuse)
- Seperation of enterprise utilities as a module: There are shared code (example: JWTUtil) between the services which can be reused across the company
- Monitoring and alerts

### Architecural Assumptions
- I don't think user should be in the same service as cycle or usage as they have a clear separation of responsibilities. Will separate them into two different services.
- This is an additon to a micro service architecture service
- There is a API Gateway which makes the JWT secure
- Daily Usage database is batch updated but still write heavy
  - This means I should not over index on the data... so my approach in using compound indexes might not be the best solution

### Architectural Improvements
- Database Maintananence - with the number of documents being updated for daily usage I can imagine archiving stale documents can be a potential way to optimatize the DB
- Logging to a Monitoring, or search platform such as Splunk for debugging / analytic purposes
- Consider sharing the database which contains the Daily Usage collection

### Testing Improvements
#### General
1. I have no unit tests :(
2. I purposely did not use @DBRef for the userId references for simplicity. This should not be the case for a proper implementation
3. Add Data Corruption Exception

#### Cycle Usage Service
1. Test data validation is working properly for service level functions
  - example: Spy on findCurrentBillingCycle or findUsageWithinCycle for correct inputs

### Model Improvements
#### User Model
1. Contact Information: such as Address, Mobile Numbers
2. Associated MDNs: and whether it is an active line with the company - Can be useful to for historical purposes but can also be queried
3. MFA information: If they enabled MFA, Security Questions (if applicable)
4. Auth Provider: If they signed up with Google or something
5. Verified Flags: Such as Email, Phone Number
6. Nofication setting: Can potentially be a part of a different document - but useful for email, mobile, app, etc. notifications
7. Security: Last Logged In, Login History
8. Preferences: Dark mode? for like web application
9. Role: Maybe it is a business ?

#### BillingCycle
1. Billing Status - Such as active (past_due, canceled) in case it has been nullified (Example: Customer stopped service)
2. Frequency: How often the customer should be billed
3. Billing Date: (if not same as start / end)
4. last payment / last payment date:
5. Service: like subscription plan

#### DailyUsage
1. usage limits (if not a part of billing cycle);

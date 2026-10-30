# Cynbit Website – Initial Technical Analysis



## 1. Objective



Analyze the publicly observable technical behaviour of the Cynbit website from a backend developer's perspective.



## 2. Website Information



- Website: https://www.cynbit.in/

- Inspected page: /contact

- Inspection date: October 30, 2026

- Inspection method: Chrome Developer Tools – Network tab

- Testing approach: Passive observation



## 3. Observed Technology Indicators



| Component | Observation |

|---|---|

| Frontend framework | Next.js indicators |

| Rendering architecture | React Server Components |

| Hosting infrastructure | Vercel |

| Transport | HTTPS |

| Compression | Brotli |

| Response content type | text/x-component |



## 4. Contact Page Request



- Request URL: /contact?\_rsc=\[redacted]

- HTTP Method: GET

- Status Code: 200 OK

- Content-Type: text/x-component

- Server: Vercel

- Matched Path: /contact.rsc



## 5. Caching Observations



Observed headers:



- Cache-Control: public, max-age=0, must-revalidate

- Age: 7262

- X-Vercel-Cache: HIT

- X-Nextjs-Stale-Time: 300



The response was served from the Vercel cache. The cache age and revalidation directives should be considered together when evaluating content freshness.



## 6. Security Observations



Positive observation:



- HTTP Strict Transport Security is configured.

- HSTS max-age is 63072000 seconds.



No authentication or authorization vulnerability was established from this page request.



## 7. Initial Conclusion



The observed contact-page request provides evidence of a Next.js React Server Components architecture hosted on Vercel.



Further investigation is required to understand contact form submission behaviour, backend API interactions, input validation and data handling.


## 8. React Server Components Response Analysis



### Observations



The Contact page returned a React Server Components payload with the content type `text/x-component`.



The response references the following JavaScript module:



`static/chunks/app/(marketing)/contact/page-a581c8d7462124d9.js`



### Additional observations



- Next.js client-side JavaScript bundles are referenced in the response.

- Google Analytics integration is present.

- JSON-LD structured metadata identifies the business as a ProfessionalService.

- Contact page metadata includes title, description, keywords and Open Graph properties.

- Next.js error and not-found component references are included in the rendering structure.



### Technical interpretation



The RSC response provides evidence of Next.js server-component routing and client-side JavaScript module loading.



The actual contact form fields and submission logic have not yet been verified from this response. Further inspection of the rendered page and its client-side behaviour is required.



### Status



Contact page RSC response: Inspected.



Contact form fields: Inspected; all five fields identified.



Form submission mechanism: Next.js Server Action named submitInquiry, identified in the client bundle. Server-side implementati.


## 9. Contact Form Analysis

### Observed Form Fields

The Cynbit Contact page displays the following fields:

| Field | Displayed Label | Required Status |
|---|---|---|
| Full Name | Full Name | Required |
| Email | Email Address | Required |
| Company | Company (Optional) | Optional |
| Industry | Industry (Optional) | Optional |
| Message | Your Message | Required |

Submission button: SEND INQUIRY

### Additional Observations

- The form provides separate fields for company and industry information.
- The message field supports longer text input.
- Company and industry are explicitly marked optional.
- Contact information is displayed alongside the inquiry form.

### Backend Design Implications

The proposed Spring Boot implementation should support:

1. Input validation for full name and email.
2. Email format validation.
3. Optional company and industry fields.
4. Message length restrictions.
5. Consistent success and error responses.
6. Persistent storage of submitted inquiries.

### Testing Status

- Contact page visually inspected.
- Five form fields identified.
- Production form submission not performed.
- Required attributes and email input type were observed. Submission mechanism was identified as a Next.js Server Action. Server-


## 10. Frontend Input Validation Analysis

### Full Name Field

Observed HTML:

```html
<input required type="text"
       placeholder="e.g. Alex Rivera"
       name="fullName" />
```

### Findings

- The field uses `type="text"`.
- The `required` attribute enables native browser validation.
- The field identifier is `fullName`.
- No maxlength, minlength or pattern attributes were present in the inspected HTML element.

### Security Consideration

Client-side validation improves user experience but cannot be trusted as the only validation layer.

The proposed Spring Boot backend should independently validate:
- Non-empty full name.
- Maximum permitted name length.
- Valid input format.
- Rejection of unexpected or excessively large input.

### Evidence Limitation

This inspection covers the Full Name input only. Other form fields and server-side validation remain unverified.



## 11. Complete Contact Form Field Analysis

The publicly accessible Cynbit contact page was inspected using Chrome Developer Tools.

### Observed fields

| Field | HTML name | Type | Required |
|---|---|---|---|
| Full Name | fullName | text | Yes |
| Email Address | email | email | Yes |
| Company | company | text | No |
| Industry | industry | text | No |
| Your Message | message | textarea | Yes |

### Additional HTML observations

- Email uses native HTML email validation.
- Message textarea has five initial rows.
- Company and Industry do not contain the HTML required attribute.
- No maxlength or minlength attributes were observed in the supplied field elements.

### Security and validation recommendations

The proposed backend should implement:

1. Server-side validation using Jakarta Bean Validation.
2. Maximum length restrictions for all text fields.
3. Email syntax validation.
4. Required-field validation.
5. Input normalization and whitespace handling.
6. Consistent HTTP error responses.
7. Protection against excessively large request payloads.

### Evidence limitations

The inspection confirms frontend HTML attributes only. Backend validation, database constraints, rate limiting, spam prevention and persistence behaviour have not yet been verified.



## 12. Contact Form Submission Architecture

### Observed implementation

Inspection of the publicly delivered JavaScript bundle for `/contact` identified a Next.js Server Action integration.

The compiled client code contains:

- Server Action reference name: `submitInquiry`
- Server Action reference ID: `40247ba4465344b3386595bab4303317cdcce907df`
- Invocation mechanism: Next.js `createServerReference` and `callServer`
- Form data collection: `new FormData(e.currentTarget)`
- Response handling: checks the returned `success` property
- Success UI: displays an "Inquiry Received" confirmation
- Failure UI: displays a generic error message
- Loading UI: disables the submit button and changes its label

### Observed submission flow

1. User submits the contact form.
2. The handler prevents the browser's default form submission.
3. The interface enters a loading state.
4. Form values are collected into a FormData object.
5. The `submitInquiry` Server Action is invoked.
6. The returned result is checked for `success`.
7. The interface displays either a confirmation or an error message.

### Potential client-side resilience issue

The inspected handler resets its loading state after the awaited Server Action resolves. No `try/catch/finally` block is visible in the handler.

If the Server Action rejects, the loading state may remain active and the submit button may stay disabled.

**Recommended improvement:** Use `try/catch/finally` to guarantee that the loading state is reset, and handle unexpected response structures safely.

### Recommended implementation pattern

```javascript
const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError(null);

    try {
        const formData = new FormData(event.currentTarget);
        const result = await submitInquiry(formData);

        if (result?.success) {
            setSuccess(true);
        } else {
            setError("Unable to submit your inquiry. Please try again.");
        }
    } catch (error) {
        setError("A temporary error occurred. Please try again.");
    } finally {
        setLoading(false);
    }
};
```

### Evidence limitations

The client-side bundle does not establish:

- Server-side validation rules.
- Database technology or persistence behaviour.
- Email delivery implementation.
- Authentication or authorization requirements.
- Rate limiting or spam protection.
- Server-side exception handling.
- Actual Server Action request and response payloads.

These items must remain unverified unless further authorized evidence becomes available.


## 13. Consolidated Findings and Assessment Status

### Verified observations

| Area | Finding | Evidence status |
|---|---|---|
| Frontend | Next.js indicators | Observed |
| Rendering | React Server Components | Observed |
| Hosting | Vercel response headers | Observed |
| Transport | HTTPS and HSTS | Observed |
| Contact form | Five input fields | Observed |
| Form submission | Next.js Server Action named `submitInquiry` | Observed in client bundle |
| Client resilience | No visible try/catch/finally in inspected handler | Observed in inspected code |

### Independent reference implementation

A separate backend has been developed using:

- Java 25 and Spring Boot 4.1.2
- Spring WebMVC and REST API
- Spring Data JPA and Hibernate
- MySQL 8.0
- Jakarta Bean Validation
- Spring Boot Actuator

Implemented endpoint:

`POST /api/v1/inquiries`

The reference implementation includes input validation, inquiry persistence, normalized email addresses, structured success responses, and centralized error handling.

### Verification results

| Test | Result |
|---|---|
| Spring application context | Passed |
| Invalid inquiry validation | Passed |
| Valid inquiry validation | Passed |
| Valid API submission | Verified |
| MySQL persistence | Verified |
| Invalid request persistence prevention | Verified |

Automated test summary: 3 tests passed, 0 failures, 0 errors.

### Recommendations

1. Validate every submitted field on the server.
2. Apply request-size limits and field-length restrictions.
3. Add rate limiting and spam prevention.
4. Handle unexpected Server Action failures using try/catch/finally.
5. Use structured server-side logging without exposing sensitive information.
6. Monitor application health and database connectivity.
7. Use database migrations for controlled schema changes.
8. Apply appropriate data retention and privacy controls to inquiry records.

### Important scope and evidence limitations

This assessment uses passive observation of publicly delivered website resources and browser-visible behaviour.

The actual Cynbit server-side source code, database, backend framework, infrastructure configuration, and internal security controls were not inspected.

The Spring Boot application in this repository is an independent reference implementation. It is not Cynbit's source code, and its architecture must not be interpreted as confirmation of Cynbit's actual backend technology.

No production form submission, vulnerability exploitation, unauthorized access, or intrusive security testing was performed.

## 14. Final Conclusion

The public contact page provides evidence of a Next.js application using React Server Components and a Next.js Server Action for inquiry submission.

The inspection identified frontend validation behaviour and a potential client-side loading-state resilience improvement.

An independent Spring Boot and MySQL reference backend was developed to demonstrate server-side validation, persistence, API response handling, and automated testing.

The resulting implementation demonstrates a practical backend engineering approach while maintaining a clear distinction between publicly observed facts and independently designed recommendations.


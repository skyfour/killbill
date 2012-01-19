/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.invoice.glue;

import org.skife.config.ConfigurationObjectFactory;

import com.google.inject.AbstractModule;
import com.ning.billing.config.InvoiceConfig;
import com.ning.billing.invoice.InvoiceListener;
import com.ning.billing.invoice.api.DefaultInvoiceService;
import com.ning.billing.invoice.api.InvoicePaymentApi;
import com.ning.billing.invoice.api.InvoiceService;
import com.ning.billing.invoice.api.invoice.DefaultInvoicePaymentApi;
import com.ning.billing.invoice.api.user.DefaultInvoiceUserApi;
import com.ning.billing.invoice.api.InvoiceUserApi;
import com.ning.billing.invoice.dao.DefaultInvoiceDao;
import com.ning.billing.invoice.dao.InvoiceDao;
import com.ning.billing.invoice.model.DefaultInvoiceGenerator;
import com.ning.billing.invoice.model.InvoiceGenerator;
import com.ning.billing.invoice.notification.DefaultNextBillingDateNotifier;
import com.ning.billing.invoice.notification.NextBillingDateNotifier;

public class InvoiceModule extends AbstractModule {

    private void installInvoiceDao() {
        bind(InvoiceDao.class).to(DefaultInvoiceDao.class).asEagerSingleton();
    }

    protected void installInvoiceUserApi() {
        bind(InvoiceUserApi.class).to(DefaultInvoiceUserApi.class).asEagerSingleton();
    }

    protected void installInvoicePaymentApi() {
        bind(InvoicePaymentApi.class).to(DefaultInvoicePaymentApi.class).asEagerSingleton();
    }

    protected void installConfig() {
        final InvoiceConfig config = new ConfigurationObjectFactory(System.getProperties()).build(InvoiceConfig.class);
        bind(InvoiceConfig.class).toInstance(config);
    }

    @Override
    protected void configure() {
        bind(InvoiceService.class).to(DefaultInvoiceService.class).asEagerSingleton();
        bind(NextBillingDateNotifier.class).to(DefaultNextBillingDateNotifier.class).asEagerSingleton();
        bind(InvoiceListener.class).asEagerSingleton();
        bind(InvoiceGenerator.class).to(DefaultInvoiceGenerator.class).asEagerSingleton();
        installConfig();
        installInvoiceDao();
        installInvoiceUserApi();
        installInvoicePaymentApi();
    }
}
